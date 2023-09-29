package com.patrick.inventorymanagementtask.models;

import java.math.BigDecimal;

/**
 * @author patrick on 8/7/20
 * @project myduka-pos
 */
public class MpesaB2CallBackRequest {
    private String conversationId, status, mpesaRef, description, names, phoneNumber, callBackUrl;
    private BigDecimal amount;

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMpesaRef() {
        return mpesaRef;
    }

    public void setMpesaRef(String mpesaRef) {
        this.mpesaRef = mpesaRef;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCallBackUrl() {
        return callBackUrl;
    }

    public void setCallBackUrl(String callBackUrl) {
        this.callBackUrl = callBackUrl;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
