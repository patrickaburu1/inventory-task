package com.patrick.inventorymanagementtask.models;

import javax.validation.constraints.NotNull;

/**
 * @author patrick on 4/21/20
 * @project myduka-pos
 */
public class ResetPasswordReq {

    @NotNull
    private String phone,code,newPassword;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
