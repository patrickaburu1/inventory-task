package com.patrick.inventorymanagementtask.security.service;


import com.patrick.inventorymanagementtask.api.models.LoginRequest;
import com.patrick.inventorymanagementtask.entities.UserVerify;
import com.patrick.inventorymanagementtask.entities.user.Role;
import com.patrick.inventorymanagementtask.entities.user.UserTypes;
import com.patrick.inventorymanagementtask.entities.user.Users;
import com.patrick.inventorymanagementtask.models.ResetPasswordReq;
import com.patrick.inventorymanagementtask.properties.ApplicationMessages;
import com.patrick.inventorymanagementtask.repositories.user.RoleRespository;
import com.patrick.inventorymanagementtask.repositories.user.UserRepository;
import com.patrick.inventorymanagementtask.repositories.user.UserTypeRepository;
import com.patrick.inventorymanagementtask.repositories.user.UserVerifyRepository;
import com.patrick.inventorymanagementtask.security.web.LoginSuccessHandler;
import com.patrick.inventorymanagementtask.utils.AppFunctions;


import com.patrick.inventorymanagementtask.utils.ResponseModel;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRespository roleRespository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserVerifyRepository userVerifyRepository;
    @Autowired
    private AppFunctions appFunctions;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserTypeRepository userTypeRepository;


    @Value("${base.url}")
    private String baseUrl;

    @Override
    public ResponseModel webSignin(LoginRequest request) {
        ResponseModel response = new ResponseModel();
        response.setStatus("01");
        if (!appFunctions.validatePhoneNumber(request.getPhoneNumber())) {
            response.setMessage("Sorry! Phone number supplied is not valid.");
            return response;
        }
        request.setPhoneNumber(appFunctions.getInternationalPhoneNumber(request.getPhoneNumber(), ""));

        Users user = userRepository.findByPhone(request.getPhoneNumber());

        if (null == user) {
            response.setMessage("Sorry! Phone number not in our records. Click get started to start.");
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

            response.setStatus("00");
            response.setMessage("Authentication successful.");
            new LoginSuccessHandler();
       /* } catch (DisabledException de) {

        }
        catch (LockedException e) {*/

        } catch (BadCredentialsException e) {
            // error.append("Sorry! Your account is inactive. Contact administrator.  ");
            response.setMessage("Sorry! Credentials don't match our records");
            response.setStatusMessage("ERR_MESSAGE_WRONG_PIN");
            response.setStatus("01");
        }
        return response;
    }

    @Override
    public Users findUserByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    @Override
    public void saveUser(Users user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        //user.setEmail(user.getPhone());
        Role userRole = roleRespository.findByRole(Role.SELF_REGISTERED);
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        user.setCreatedOn(new Date());
        user.setUserType(userTypeRepository.findFirstByName(UserTypes.PORTAL_USER_TYPE).getId());
        userRepository.save(user);
    }

    @Override
    public Map<String, Object> resetPassword(String phone) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "01");
        if (!appFunctions.validatePhoneNumber(phone)) {
            response.put("message", "Supplied phone is not a valid phone");
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


            String message = String.format(ApplicationMessages.get("sms.password.reset"), code);
            //  notificationService.sendSms(null,user, user.getPhone(), message);

            response.put("status", "00");
            response.put("message", "A confirmation code has been sent to your phone.");

        } else if (null != user && user.getActive() == 0) {

            response.put("message", "Sorry account associated email is suspended.");
        } else {
            response.put("message", "Sorry no user associated with supplied username.");
        }
        return response;
    }

    @Override
    public Map<String, Object> newPassword(ResetPasswordReq resetPasswordReq) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "01");

        if (!appFunctions.validatePhoneNumber(resetPasswordReq.getPhone())) {
            response.put("message", "Supplied phone is not a valid phone");
            return response;
        }
        String phone = appFunctions.getInternationalPhoneNumber(resetPasswordReq.getPhone(), "");

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
}
