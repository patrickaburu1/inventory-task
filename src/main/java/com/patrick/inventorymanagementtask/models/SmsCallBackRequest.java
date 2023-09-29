package com.patrick.inventorymanagementtask.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author patrick on 5/18/20
 * @project myduka-pos
 */
public class SmsCallBackRequest {
    @JsonProperty("phoneNumber")
    private String phoneNumber;
    @JsonProperty("retryCount")
    private String retryCount;
    @JsonProperty("id")
    private String id;
    @JsonProperty("status")
    private String status;
    @JsonProperty("networkCode")
    private String networkCode;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(String retryCount) {
        this.retryCount = retryCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNetworkCode() {
        return networkCode;
    }

    public void setNetworkCode(String networkCode) {
        this.networkCode = networkCode;
    }
}
