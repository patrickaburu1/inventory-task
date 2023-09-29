package com.patrick.inventorymanagementtask.models.sms;

import java.math.BigDecimal;

/**
 * @author patrick on 9/18/21
 * @project myduka-pos
 */
public class SetUpOrgInSmsGateway {
    private String name,description,orgEmail,orgPhone,website;
    private Long senderId;
    private Long referredBy;
    private BigDecimal smsRate=BigDecimal.ONE;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrgEmail() {
        return orgEmail;
    }

    public void setOrgEmail(String orgEmail) {
        this.orgEmail = orgEmail;
    }

    public String getOrgPhone() {
        return orgPhone;
    }

    public void setOrgPhone(String orgPhone) {
        this.orgPhone = orgPhone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReferredBy() {
        return referredBy;
    }

    public void setReferredBy(Long referredBy) {
        this.referredBy = referredBy;
    }

    public BigDecimal getSmsRate() {
        return smsRate;
    }

    public void setSmsRate(BigDecimal smsRate) {
        this.smsRate = smsRate;
    }
}
