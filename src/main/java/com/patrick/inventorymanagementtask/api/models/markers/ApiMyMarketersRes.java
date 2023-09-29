package com.patrick.inventorymanagementtask.api.models.markers;

import java.math.BigDecimal;

/**
 * @author patrick on 7/27/20
 * @project inventory
 */
public class ApiMyMarketersRes {
    private String names;
    private String phone;
    private String referralCode;
    private Integer shopsReferredCount;
    private String status;
    private BigDecimal rate;

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public Integer getShopsReferredCount() {
        return shopsReferredCount;
    }

    public void setShopsReferredCount(Integer shopsReferredCount) {
        this.shopsReferredCount = shopsReferredCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
