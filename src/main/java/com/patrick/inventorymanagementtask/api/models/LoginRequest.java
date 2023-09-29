package com.patrick.inventorymanagementtask.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotNull;

/**
 * @author patrick on 2/11/20
 * @project  inventory
 */
public class LoginRequest {

    @NotNull
    private String phoneNumber,password;
    @NonNull
    private String appVersion;

   // @NonNull
    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(@NonNull String appVersion) {
        this.appVersion = appVersion;
    }

    @JsonProperty("f_token")
    private String fireBaseToken;

    public String getFireBaseToken() {
        return fireBaseToken;
    }

    public void setFireBaseToken(String fireBaseToken) {
        this.fireBaseToken = fireBaseToken;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
