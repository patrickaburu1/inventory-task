package com.patrick.inventorymanagementtask.api.models.markers;

import org.springframework.lang.NonNull;

import java.math.BigDecimal;

/**
 * @author patrick on 7/27/20
 * @project inventory
 */
public class ApiNewMarketerReq {
    @NonNull
    private String phoneNumber;
    @NonNull
    private BigDecimal rate;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
