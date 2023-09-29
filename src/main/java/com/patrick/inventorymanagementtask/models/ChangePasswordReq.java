package com.patrick.inventorymanagementtask.models;

import org.springframework.lang.NonNull;

/**
 * @author patrick on 6/14/20
 * @project myduka-pos
 */
public class ChangePasswordReq {
    @NonNull
    private String oldPassword,newPassword, confirmPassword;

    @NonNull
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(@NonNull String oldPassword) {
        this.oldPassword = oldPassword;
    }

    @NonNull
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(@NonNull String newPassword) {
        this.newPassword = newPassword;
    }

    @NonNull
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(@NonNull String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
