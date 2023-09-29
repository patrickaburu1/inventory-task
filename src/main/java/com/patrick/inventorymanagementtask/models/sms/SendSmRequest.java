package com.patrick.inventorymanagementtask.models.sms;

/**
 * @author patrick on 7/20/21
 * @project myduka-pos
 */
public class SendSmRequest {
    private String message;
    private String senderId;
    private String phoneNumber;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
