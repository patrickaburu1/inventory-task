package com.patrick.inventorymanagementtask.models;

import java.math.BigDecimal;

/**
 * @author patrick on 8/7/20
 * @project myduka-pos
 */
public class MpesaB2CRequest {
    private String phoneNumber;
    private BigDecimal amount;
    private String shortCode;
    private String callBackUrl;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getCallBackUrl() {
        return callBackUrl;
    }

    public void setCallBackUrl(String callBackUrl) {
        this.callBackUrl = callBackUrl;
    }
}
