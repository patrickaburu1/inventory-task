package com.patrick.inventorymanagementtask.api.models;

import javax.validation.constraints.NotNull;

/**
 * @author patrick on 11/21/21
 * @project inventory
 */
public class ApiPromoValidateReq {
    @NotNull
    private Long shopId;
    @NotNull
    private String promoCode;

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }
}
