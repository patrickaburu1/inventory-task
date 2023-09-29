package com.patrick.inventorymanagementtask.api.models.markers;

import java.math.BigDecimal;

/**
 * @author patrick on 7/26/20
 * @project inventory
 */
public class MarketerInfoRes {
    private String referralCode;
    private String marketerType;
    private BigDecimal rate;

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public String getMarketerType() {
        return marketerType;
    }

    public void setMarketerType(String marketerType) {
        this.marketerType = marketerType;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
