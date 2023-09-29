package com.patrick.inventorymanagementtask.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author patrick on 4/14/21
 * @project myduka-pos
 */
public class SmsResponseAdvants {

    private List<AdvantaResponse> responses;

    public List<AdvantaResponse> getResponses() {
        return responses;
    }

    public void setResponses(List<AdvantaResponse> responses) {
        this.responses = responses;
    }

    public static class AdvantaResponse{
        @JsonProperty("response-code")
        private Integer responseCode;
        @JsonProperty("response-description")
        private String responseDescription;
        @JsonProperty("mobile")
        private String mobile;
        @JsonProperty("messageid")
        private String messageid;
        @JsonProperty("networkid")
        private String networkid;

        public Integer getResponseCode() {
            return responseCode;
        }

        public void setResponseCode(Integer responseCode) {
            this.responseCode = responseCode;
        }

        public String getResponseDescription() {
            return responseDescription;
        }

        public void setResponseDescription(String responseDescription) {
            this.responseDescription = responseDescription;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getMessageid() {
            return messageid;
        }

        public void setMessageid(String messageid) {
            this.messageid = messageid;
        }

        public String getNetworkid() {
            return networkid;
        }

        public void setNetworkid(String networkid) {
            this.networkid = networkid;
        }
    }
}
