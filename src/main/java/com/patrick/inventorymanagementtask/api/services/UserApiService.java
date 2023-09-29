package com.patrick.inventorymanagementtask.api.services;

import com.patrick.inventorymanagementtask.api.models.ApiChangePasswordReq;
import com.patrick.inventorymanagementtask.api.models.ApiUpdateProfileReq;
import com.patrick.inventorymanagementtask.api.models.LoginRequest;
import com.patrick.inventorymanagementtask.api.models.PhoneVerificationReq;
import com.patrick.inventorymanagementtask.api.models.markers.MarketerInfoRes;
import com.patrick.inventorymanagementtask.config.AppSettingService;
import com.patrick.inventorymanagementtask.entities.PhoneVerification;
import com.patrick.inventorymanagementtask.entities.UserVerify;
import com.patrick.inventorymanagementtask.entities.marketers.Marketers;
import com.patrick.inventorymanagementtask.entities.products.Shop;
import com.patrick.inventorymanagementtask.entities.user.UserTypes;
import com.patrick.inventorymanagementtask.entities.user.Users;
import com.patrick.inventorymanagementtask.models.ResetPasswordReq;
import com.patrick.inventorymanagementtask.properties.ApplicationMessages;
import com.patrick.inventorymanagementtask.repositories.MarketersRepository;
import com.patrick.inventorymanagementtask.repositories.PhoneVerificationRepository;
import com.patrick.inventorymanagementtask.repositories.user.UserRepository;
import com.patrick.inventorymanagementtask.repositories.user.UserTypeRepository;
import com.patrick.inventorymanagementtask.repositories.user.UserVerifyRepository;
import com.patrick.inventorymanagementtask.security.api.TokenHelper;
import com.patrick.inventorymanagementtask.security.service.CustomUserDetailsService;
import com.patrick.inventorymanagementtask.service.DashboardService;
import com.patrick.inventorymanagementtask.service.ShopService;
import com.patrick.inventorymanagementtask.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author patrick on 2/11/20
 * @project  inventory
 */
@Service
@Transactional
public class UserApiService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenHelper tokenHelper;
    @Autowired
    private PhoneVerificationRepository phoneVerificationRepository;
    @Autowired
    private AppFunctions appFunctions;

    @Autowired
    private ShopService shopService;
    @Autowired
    private UserTypeRepository userTypeRepository;
    @Autowired
    private UserVerifyRepository userVerifyRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private DashboardService dashboardService;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private AppSettingService appSettingsService;
    @Autowired
    private MarketersRepository marketersRepository;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${jwt.expires_in}")
    private long expiresIn;

    /**
     * check app version
     */
    public Boolean isVersionSupported(String version) {
        String[] versions = appSettingsService.getAppSettings(AppConstants.APP_SETTINGS_VERSION).getValue().split(",");

        List versionList = Arrays.asList(versions);
        return versionList.contains(version);
    }

    /**
     * check app version
     */
    public Boolean isVersionSupportedMarketing(String version) {
        String[] versions = appSettingsService.getAppSettings(AppConstants.APP_SETTINGS_VERSION_MARKETING).getValue().split(",");

        List versionList = Arrays.asList(versions);
        return versionList.contains(version);
    }

    /**
     * mobile login
     */
    public Map<String, Object> login(LoginRequest request, HttpServletRequest httpServletRequest) {
        Map<String, Object> response = new HashMap<>();

        String ip = httpServletRequest.getHeader("X-Forwarded-For") == null ? httpServletRequest.getRemoteAddr() : httpServletRequest.getHeader("X-Forwarded-For");

        if (!appFunctions.validatePhoneNumber(request.getPhoneNumber())) {
            response.put("message", "Sorry Invalid phone");
            return response;
        }
        request.setPhoneNumber(appFunctions.getInternationalPhoneNumber(request.getPhoneNumber(), ""));

        UserTypes userType = userTypeRepository.findFirstByName(UserTypes.PORTAL_USER_TYPE);
        Users user = userRepository.findByPhoneAndUserType(request.getPhoneNumber(), userType.getId());

        if (null == user) {
            response.put("status", "01");
            response.put("statusMessage", "NOT_REGISTERED");
            response.put("message", "Sorry your not registered.");
            return response;
        }

        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    user.getPhone(),
                    request.getPassword()
            );

            //  usernamePasswordAuthenticationToken.setDetails(loginIpAddress);

            final Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

            // Inject into security context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String phoneNumber = null;

            if (authentication.getPrincipal() instanceof Users)
                phoneNumber = ((Users) authentication.getPrincipal()).getPhone();
            else
                phoneNumber = (String) authentication.getName();

            logger.info("*******PHONE_NUMBER_TOKEN: " + phoneNumber);

            String jwToken = tokenHelper.generateToken(phoneNumber);

            if (null == request.getAppVersion() || request.getAppVersion().isEmpty()) {
                response.put("status", "01");
                response.put("statusMessage", "UPDATE_APP");
                response.put("message", "Sorry! App version not supported. Please update to the latest version.");
                return response;
            }

            if (null != request.getAppVersion() && !request.getAppVersion().isEmpty()) {
                if (!isVersionSupported(request.getAppVersion())) {
                    response.put("status", "01");
                    response.put("statusMessage", "UPDATE_APP");
                    response.put("message", "Sorry! App version not supported. Please update to the latest version.");
                    return response;
                }
                user.setAppVersion(request.getAppVersion());
            }

            Shop defaultShop=shopService.getUserDefaultShop(user);
            response.put("status", "00");
            response.put("accessToken", jwToken);
            response.put("expiry", expiresIn * 1000);
            response.put("message", "Authentication successful.");
            response.put("defaultShop",defaultShop );
            response.put("hasProducts",shopService.ifShopHasProducts(defaultShop) );
            response.put("user", user);

            user.setLastLogin(new Date());
            user.setIp(ip);
            userRepository.save(user);

            dashboardService.saveAuditLogMobile("Login", user, httpServletRequest);

            if (null != request.getFireBaseToken() && !request.getFireBaseToken().isEmpty()) {
                user.setFireBaseToken(request.getFireBaseToken());
            }
       /* } catch (DisabledException de) {

        }
        catch (LockedException e) {*/

        } catch (BadCredentialsException e) {
            // error.append("Sorry! Your account is inactive. Contact administrator.  ");
            response.put("message", "Sorry invalid credentials");
            response.put("statusMessage", "ERR_MESSAGE_WRONG_PIN");
            response.put("status", "01");
        }
        return response;
    }


    /**
     * marketing app login
     */
    public Map<String, Object> marketingLogin(LoginRequest request, HttpServletRequest httpServletRequest) {
        Map<String, Object> response = new HashMap<>();

        String ip = httpServletRequest.getHeader("X-Forwarded-For") == null ? httpServletRequest.getRemoteAddr() : httpServletRequest.getHeader("X-Forwarded-For");

        if (!appFunctions.validatePhoneNumber(request.getPhoneNumber())) {
            response.put("message", "Sorry Invalid phone number");
            return response;
        }
        request.setPhoneNumber(appFunctions.getInternationalPhoneNumber(request.getPhoneNumber(), ""));

        UserTypes userType = userTypeRepository.findFirstByName(UserTypes.PORTAL_USER_TYPE);
        Users user = userRepository.findByPhoneAndUserType(request.getPhoneNumber(), userType.getId());

        if (null == user) {
            response.put("status", "01");
            response.put("statusMessage", "NOT_REGISTERED");
            response.put("message", "Sorry  invalid Credentials");
            return response;
        }

        Marketers marketer=marketersRepository.findFirstByUserId(user.getId());
        if (null==marketer){
            response.put("status", "01");
            response.put("statusMessage", "NOT_A_MARKETER");
            response.put("message", "Sorry! Access denied.");
            return response;
        }
        if (!marketer.getFlag().equalsIgnoreCase(AppConstants.ACTIVE_RECORD)){
            response.put("status", "01");
            response.put("statusMessage", "ACCOUNT_NOT_ACTIVE");
            response.put("message", "Sorry! Invalid credentials.");
            return response;
        }
        if (marketer.getStatus().equalsIgnoreCase(Marketers.MARKETER_STATUS_PENDING)){
            response.put("status", "01");
            response.put("statusMessage", "ACCOUNT_PENDING_APPROVAL");
            response.put("message", "Sorry! Invalid credentials.");
            return response;
        }
        if (marketer.getStatus().equalsIgnoreCase(Marketers.MARKETER_STATUS_SUSPENDED)){
            response.put("status", "01");
            response.put("statusMessage", "ACCOUNT_SUSPENDED");
            response.put("message", "Sorry! Invalid credentials.");
            return response;
        }

        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    user.getPhone(),
                    request.getPassword()
            );

            final Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

            // Inject into security context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String phoneNumber = null;

            if (authentication.getPrincipal() instanceof Users)
                phoneNumber = ((Users) authentication.getPrincipal()).getPhone();
            else
                phoneNumber = (String) authentication.getName();

            logger.info("*******PHONE_NUMBER_TOKEN: " + phoneNumber);

            String jwToken = tokenHelper.generateMartekingUserToken(phoneNumber);



            if (null == request.getAppVersion() || request.getAppVersion().isEmpty()) {
                response.put("status", "01");
                response.put("statusMessage", "UPDATE_APP");
                response.put("message", "Sorry! App version not supported. Please update to the latest version.");
                return response;
            }

            if (null != request.getAppVersion() && !request.getAppVersion().isEmpty()) {
                if (!isVersionSupportedMarketing(request.getAppVersion())) {
                    response.put("status", "01");
                    response.put("statusMessage", "UPDATE_APP");
                    response.put("message", "Sorry! App version not supported. Please update to the latest version.");
                    return response;
                }
              //  user.setAppVersion(request.getAppVersion());
            }

            marketer.setFirabaseToken(request.getFireBaseToken());
            marketer.setLastLogin(new Date());
            marketer.setAppVersion(request.getAppVersion());
            marketer.setIp(ip);
            marketersRepository.save(marketer);

            MarketerInfoRes marketerInfoRes=new MarketerInfoRes();
            marketerInfoRes.setMarketerType(marketer.getMarketerType());
            marketerInfoRes.setReferralCode(marketer.getReferralCode());
            marketerInfoRes.setRate(marketer.getPercentRate());

            response.put("status", "00");
            response.put("accessToken", jwToken);
            response.put("expiry", 1200 * 1000);
            response.put("message", "Authentication successful.");
            response.put("user", user);
            response.put("marketerInfo", marketerInfoRes);

            dashboardService.saveAuditLogMobile("Login", user, httpServletRequest);

            if (null != request.getFireBaseToken() && !request.getFireBaseToken().isEmpty()) {
                user.setFireBaseToken(request.getFireBaseToken());
            }
       /* } catch (DisabledException de) {

        }
        catch (LockedException e) {*/

        } catch (BadCredentialsException e) {
            // error.append("Sorry! Your account is inactive. Contact administrator.  ");
            response.put("message", "Sorry invalid credentials");
            response.put("statusMessage", "ERR_MESSAGE_WRONG_PIN");
            response.put("status", "01");
        }
        return response;
    }



    public Map<String, Object> users() {
        Map<String, Object> response = new HashMap<>();
        Iterable<Users> users = userRepository.findAll();
        response.put("status", "00");
        response.put("message", "success");
        response.put("data", users);
        return response;
    }

    public ResponseModel verifyPhone(PhoneVerificationReq req, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (!appFunctions.validatePhoneNumber(req.getPhoneNumber())) {
            return new ResponseModel("01", "Sorry! Invalid phone number.");
        }
        req.setPhoneNumber(appFunctions.getInternationalPhoneNumber(req.getPhoneNumber(), ""));
        Users user = userRepository.findByPhone(req.getPhoneNumber());
        if (null != user) {
            return new ResponseModel("REGISTERED", "00", "Registered login to continue.");
        }
        PhoneVerification phoneVerification = phoneVerificationRepository.findByPhoneNumber(req.getPhoneNumber());
        if (null == phoneVerification) {
            phoneVerification = new PhoneVerification();
        }
        phoneVerification.setPhoneNumber(req.getPhoneNumber());
        phoneVerification.setVerified(false);
        phoneVerification.setCreatedOn(new Date());
        phoneVerification.setCode(appFunctions.randomCodeNumber(4));
        phoneVerificationRepository.save(phoneVerification);

        session.setAttribute("_phone", req.getPhoneNumber());
        //sms
        Map<String, Object> map = new HashMap<>();
        map.put("code", phoneVerification.getCode());
        return new ResponseModel("VERIFY_PHONE", "00", "An OTP has been sent to you phone to verify phone number.", map);
    }

    public ResponseModel verifyOTP(PhoneVerificationReq req) {
        if (!appFunctions.validatePhoneNumber(req.getPhoneNumber())) {
            return new ResponseModel("01", "Sorry! Invalid phone number.");
        }
        req.setPhoneNumber(appFunctions.getInternationalPhoneNumber(req.getPhoneNumber(), ""));
        Users user = userRepository.findByPhone(req.getPhoneNumber());
        if (null != user) {
            return new ResponseModel("REGISTERED", "01", "Sorry! Already registered login to proceed.");
        } else if (null == req.getCode() || req.getCode().isEmpty()) {
            return new ResponseModel("CODE_REQUIRED", "01", "Verification code is required.");
        }
        PhoneVerification phoneVerification = phoneVerificationRepository.findByPhoneNumberAndCode(req.getPhoneNumber(), req.getCode());
        if (null == phoneVerification) {
            return new ResponseModel("01", "Sorry! invalid verification code.");
        } else if (phoneVerification.getVerified()) {
            return new ResponseModel("01", "Sorry! phone number already verified.");
        }
        phoneVerification.setVerified(true);
        phoneVerificationRepository.save(phoneVerification);
        return new ResponseModel("00", "Success, Phone verified.");
    }

    /**
     * reset password
     */
    public ResponseModel forgotPassword(String phone) {
        ResponseModel response = new ResponseModel();
        response.setStatus("01");
        if (!appFunctions.validatePhoneNumber(phone)) {
            response.setMessage("Supplied phone is not a valid phone");
            return response;
        }
        phone = appFunctions.getInternationalPhoneNumber(phone, "");

        Users user = userRepository.findByPhone(phone);

        if (null != user && user.getActive() == 1) {

            UUID uuid = UUID.randomUUID();
            String code = appFunctions.randomCodeNumber(4);
            UserVerify userVerify = userVerifyRepository.findByUserName(user.getPhone());
            if (null != userVerify) {
                userVerify.setCode(code)
                        .setActive(0);
            } else {
                userVerify = new UserVerify();
                userVerify
                        .setCode(code)
                        .setActive(0)
                        .setUserName(user.getPhone())
                        .setUpdatedOn(new Date());
            }
            userVerifyRepository.save(userVerify);


            String message =   String.format(ApplicationMessages.get("sms.password.reset")  , code);

            Map<String, Object> map = new HashMap<>();
            map.put("code", code);
            response.setStatus("00");
            response.setMessage("A confirmation code has been sent to your phone.");
            response.setData(map);

        } else if (null != user && user.getActive() == 0) {

            response.setMessage("Sorry account associated email is suspended.");
        } else {
            response.setMessage("Sorry no user associated with supplied username.");
        }
        return response;
    }

    public Map<String, Object> resetPassword(ResetPasswordReq resetPasswordReq) {
        Map<String, Object> response = new HashMap<>();
        if (!appFunctions.validatePhoneNumber(resetPasswordReq.getPhone())) {
            response.put("status", "01");
            response.put("message", "Supplied phone is not a valid phone");
            return response;
        }
        String phone = appFunctions.getInternationalPhoneNumber(resetPasswordReq.getPhone(), "");

        response.put("status", "01");

        UserVerify userVerify = userVerifyRepository.findByCodeAndUserName(resetPasswordReq.getCode(), phone);

        if (null != userVerify && userVerify.getActive() == 0) {

            Users user = userRepository.findByPhone(userVerify.getUserName());
            if (null == user) {
                response.put("message", "Sorry! Supplied phone doesn't have an account with us.");
                return response;
            }
            user.setPassword(bCryptPasswordEncoder.encode(resetPasswordReq.getNewPassword()));
            userRepository.save(user);
            userVerify.setActive(1);
            userVerifyRepository.save(userVerify);
            response.put("status", "00");
            response.put("message", "Password was reset successfully. Use new password to login.");

        } else if (null != userVerify && userVerify.getActive() == 1) {
            response.put("message", "Confirmation code already used. Request for password reset again.");
        } else {
            response.put("message", "Sorry invalid confirmation code. Supply the correct confirmation code.");
        }
        return response;
    }

    /**
     * updates user profile
     */
    public ResponseModel updateProfile(ApiUpdateProfileReq req) {
        Users user = userDetailsService.getAuthicatedUser();
        if (null == user)
            return new ResponseModel("01", "Sorry your are not an active user");

        if (null != req.getEmail() && !req.getEmail().isEmpty()) {
            if (null != user.getEmail() && !user.getEmail().equalsIgnoreCase(req.getEmail())) {
                Users users = userRepository.findByEmail(req.getEmail());
                if (null != users)
                    return new ResponseModel("01", "Email address already exist with another account");
                user.setEmail(req.getEmail());
            } else {
                user.setEmail(req.getEmail());
            }
        }
        user.setFirstname(req.getFirstName());
        user.setLastname(req.getLastName());
        userRepository.save(user);
        return new ResponseModel("00", "Success profile updated successfully.");
    }

    /**
     * change password
     */
    public ResponseModel changePassword(ApiChangePasswordReq req) {
        Users user = userDetailsService.getAuthicatedUser();
        if (null == user)
            return new ResponseModel("01", "Sorry your are not an active user");

        if (!SystemComponent.passwordMacthes(req.getOldPassword(), user))
            return new ResponseModel("01", "Sorry old password doesn't match our records.");
        if (req.getOldPassword().equals(req.getNewPassword()))
            return new ResponseModel("01", "Sorry new password cannot be the sames as old password.");


        user.setPassword(bCryptPasswordEncoder.encode(req.getNewPassword()));
        userRepository.save(user);

        return new ResponseModel("00", "Successfully changed password");
    }
}
