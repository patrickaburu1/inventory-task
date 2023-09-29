package com.patrick.inventorymanagementtask.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

/**
 * @author patrick on 6/1/20
 * @project inventory
 */
public class ApiShopSubscribeRequest {
    @NotNull
    private String phone;
    @NotNull
    private Long shopId;
    @NotNull
    private Long planId;
    private String promoCode;

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    @JsonProperty("f_token")
    private String fToken;

    public String getfToken() {
        return fToken;
    }

    public void setfToken(String fToken) {
        this.fToken = fToken;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }
}
