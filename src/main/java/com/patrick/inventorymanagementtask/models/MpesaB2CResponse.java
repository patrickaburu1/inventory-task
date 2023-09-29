package com.patrick.inventorymanagementtask.models;

/**
 * @author patrick on 8/7/20
 * @project myduka-pos
 */
public class MpesaB2CResponse {
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

    public static class Data {
        String conversionId;

        public String getConversionId() {
            return conversionId;
        }

        public void setConversionId(String conversionId) {
            this.conversionId = conversionId;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "conversionId='" + conversionId + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MpesaB2CResponse{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
