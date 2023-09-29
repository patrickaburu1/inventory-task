package com.patrick.inventorymanagementtask.models;

/**
 * @author patrick on 7/6/20
 * @project myduka-pos
 */
public class MpesaStkResponse {

    private String status;

    private String message;

    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data{
        String checkOutId;

        public String getCheckOutId() {
            return checkOutId;
        }

        public void setCheckOutId(String checkOutId) {
            this.checkOutId = checkOutId;
        }

        @Override
        public String toString() {
            return "{" +
                    "checkOutId='" + checkOutId + '\'' +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "MpesaStkResponse{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
