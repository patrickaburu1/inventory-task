package com.patrick.inventorymanagementtask.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.patrick.inventorymanagementtask.api.models.ApiChangePasswordReq;
import com.patrick.inventorymanagementtask.api.models.ApiUpdateProfileReq;
import com.patrick.inventorymanagementtask.api.models.LoginRequest;
import com.patrick.inventorymanagementtask.api.models.PhoneVerificationReq;
import com.patrick.inventorymanagementtask.api.services.UserApiService;
import com.patrick.inventorymanagementtask.entities.PhoneVerification;
import com.patrick.inventorymanagementtask.entities.user.Users;
import com.patrick.inventorymanagementtask.models.ResetPasswordReq;
import com.patrick.inventorymanagementtask.repositories.PhoneVerificationRepository;
import com.patrick.inventorymanagementtask.repositories.user.UserRepository;
import com.patrick.inventorymanagementtask.security.api.TokenService;
import com.patrick.inventorymanagementtask.security.service.UserService;
import com.patrick.inventorymanagementtask.utils.AppConstants;
import com.patrick.inventorymanagementtask.utils.AppFunctions;
import com.patrick.inventorymanagementtask.utils.ResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author patrick on 2/11/20
 * @project shop-pos
 */
@RestController
public class ApiUserController {
    @Autowired
    private UserApiService userApiService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PhoneVerificationRepository phoneVerificationRepository;
    @Autowired
    private AppFunctions appFunctions;

    private Logger logger= LoggerFactory.getLogger(getClass());

    @PostMapping(value = "/api/v1/verify-phone")
    public ResponseModel verifyPhone(@RequestBody @Valid PhoneVerificationReq req, HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();

        ResponseModel response=userApiService.verifyPhone(req, request);
        // Get the json string
        try {
            String json = mapper.writeValueAsString(response);
            logger.info("*************** check if phone is registered response "+json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return response;

    }

    @PostMapping(value = "/api/v1/verify-otp")
    public ResponseModel verifyOtp(@RequestBody @Valid PhoneVerificationReq request) {
        return userApiService.verifyOTP(request);

    }

    @PostMapping(value = "/api/v1/login")
    public ResponseEntity login(@RequestBody @Valid LoginRequest request, HttpServletRequest httpServletRequest) {
        Map<String, Object> response = userApiService.login(request,httpServletRequest);
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(response);
            logger.info("*************** api login request {} "+mapper.writeValueAsString(request));
            logger.info("*************** api login response "+json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/api/v1/marketing/login")
    public ResponseEntity marketingLogin(@RequestBody @Valid LoginRequest request, HttpServletRequest httpServletRequest) {
        Map<String, Object> response = userApiService.marketingLogin(request,httpServletRequest);
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(response);
            logger.info("*************** api login marketing  request {} "+mapper.writeValueAsString(request));
            logger.info("*************** api login marketing response "+json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(response);
    }


 /*   @GetMapping(value = "/api/v1/users")
    public ResponseEntity users(@RequestHeader("Authorization") String authHeader,
                                @RequestHeader(value = "Access-Key",required = false) String accessKey) {
        Map<String, Object> response = userApiService.users();
       *//* Map<String, Object> refreshTokenRes = refreshToken(authHeader, accessKey);
        response.put("refreshToken", false);
        if (refreshTokenRes.get("status").toString().equalsIgnoreCase("00")) {
            response.put("refreshedToken", refreshTokenRes.get("refreshToken"));
            response.put("refreshToken", true);
        }*//*
        return ResponseEntity.ok(response);
    }*/

    @RequestMapping(value = {"/api/v1/signup"}, method = RequestMethod.POST)
    public ResponseEntity createUser(@Valid @RequestBody Users users) {
        ResponseModel response;
        if (!appFunctions.validatePhoneNumber(users.getPhone())) {
            response = new ResponseModel("01", "Sorry! Invalid phone number.");
            return ResponseEntity.ok(response);
        }
        users.setPhone(appFunctions.getInternationalPhoneNumber(users.getPhone(), ""));
        Users usersExists = userRepository.findByPhone(users.getPhone());
        PhoneVerification phoneVerification = phoneVerificationRepository.findByPhoneNumber(users.getPhone());
        if (null != usersExists) {
            response = new ResponseModel("01", "This phone number already exists!");
        } else if (null == phoneVerification || !phoneVerification.getVerified()) {
            response = new ResponseModel("01", "Sorry! phone number not verified!");
        } else {
            userService.saveUser(users);
            response = new ResponseModel("00", "Account created successfully.");
        }
        return ResponseEntity.ok(response);
    }


    @PostMapping(value = "/api/v1/password/forgot")
    public ResponseModel forgotPassword(@RequestParam("phoneNumber") @NotNull String phoneNumber) {
        return userApiService.forgotPassword(phoneNumber);
    }

    @PostMapping(value = "/api/v1/password/reset")
    public Map<String, Object> resetPassword(@Valid @RequestBody ResetPasswordReq resetPasswordReq) {
        return userApiService.resetPassword(resetPasswordReq);
    }

    @PostMapping(value = "/api/v1/change-password")
    public ResponseModel changePassword(@RequestHeader("Authorization") String authHeader,@Valid @RequestBody ApiChangePasswordReq req) {

        ResponseModel response= userApiService.changePassword(req);
        response.setRefreshedToken(refreshedToken(authHeader));
        return response;
    }


    @GetMapping(value = "/api/v1/refresh-token")
    public ResponseModel refreshUserToken(@RequestHeader("Authorization") String authHeader,
                                           @NotNull @RequestHeader("Access-Key") String accessKey) {
        return refreshToken(authHeader);
    }

    @PostMapping(value = "/api/v1/update-profile")
    public ResponseModel updateProfile(@RequestHeader("Authorization") String authHeader,
                                       @RequestBody ApiUpdateProfileReq req) {
        return userApiService.updateProfile(req);
    }


    private String refreshedToken(String authHeader) {
        ResponseModel response = tokenService.refreshAccessToken(authHeader);
        if (response.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return response.getRefreshedToken();
        else
            return null;
    }


    private ResponseModel refreshToken(String authHeader) {
        return tokenService.refreshAccessToken(authHeader);
    }
}
