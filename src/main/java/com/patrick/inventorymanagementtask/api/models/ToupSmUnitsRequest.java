package com.patrick.inventorymanagementtask.api.models;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author patrick on 7/22/21
 * @project inventory
 */
public class ToupSmUnitsRequest {
    @NotNull
    private String phoneNumber;
    @NotNull
    private BigDecimal amountPaid;
    @NotNull
    private Long shopId;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }
}
