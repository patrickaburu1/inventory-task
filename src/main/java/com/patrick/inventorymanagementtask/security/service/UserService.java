package com.patrick.inventorymanagementtask.security.service;




import com.patrick.inventorymanagementtask.api.models.LoginRequest;
import com.patrick.inventorymanagementtask.entities.user.Users;
import com.patrick.inventorymanagementtask.models.ResetPasswordReq;
import com.patrick.inventorymanagementtask.utils.ResponseModel;

import java.util.Map;

public interface UserService {

    public ResponseModel webSignin(LoginRequest request);
    public Users findUserByPhone(String phone);

    public void saveUser(Users users);

    public Map<String,Object> resetPassword(String phone);

    public Map<String,Object> newPassword(ResetPasswordReq resetPasswordReq);
}
