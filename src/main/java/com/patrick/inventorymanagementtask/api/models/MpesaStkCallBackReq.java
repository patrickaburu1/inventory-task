package com.patrick.inventorymanagementtask.api.models;

/**
 * @author patrick on 7/6/20
 * @project inventory
 */
public class MpesaStkCallBackReq {
    private String checkOutId,status, mpesaRef,description,callBackUrl;

    public String getCheckOutId() {
        return checkOutId;
    }

    public void setCheckOutId(String checkOutId) {
        this.checkOutId = checkOutId;
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

    public String getCallBackUrl() {
        return callBackUrl;
    }

    public void setCallBackUrl(String callBackUrl) {
        this.callBackUrl = callBackUrl;
    }
}
