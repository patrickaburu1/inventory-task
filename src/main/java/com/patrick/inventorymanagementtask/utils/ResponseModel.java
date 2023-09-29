package com.patrick.inventorymanagementtask.utils;

public class ResponseModel{

    private String statusMessage;

    private String status;

    private String message;

    private Object data;

    private String refreshedToken;

    public ResponseModel() {

    }

    public ResponseModel(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseModel(String statusMessage, String status, String message) {
        this.statusMessage = statusMessage;
        this.status = status;
        this.message = message;
    }

    public ResponseModel(String status, String message, Object data) {
        this(status, message);
        this.data = data;
    }

    public ResponseModel(String statusMessage, String status, String message, Object data) {
        this.statusMessage = statusMessage;
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getRefreshedToken() {
        return refreshedToken;
    }

    public void setRefreshedToken(String refreshedToken) {
        this.refreshedToken = refreshedToken;
    }

    @Override
    public String toString(){
        if(null != statusMessage)
            return "{ \"status\": \"" + this.status + "\",\"message\":\"" + this.message + "\", \"statusMessage\":\"" + this.statusMessage + "\" }";
        else
            return "{ \"status\": \"" + this.status + "\",\"message\":\"" + this.message +  "\",\"data\":\""+this.data+"\"}";
    }
}
