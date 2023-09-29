package com.patrick.inventorymanagementtask.models;

import org.springframework.lang.NonNull;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author patrick on 7/6/20
 * @project myduka-pos
 */
public class MpesaStkReq {
    @NotNull
    private BigDecimal amount;

    @NotNull
    private String msisdn;

    @NonNull
    private String tranRef;

    @NotNull
    private String shortCode;

    @NotNull
    private String callBackUrl;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    @NonNull
    public String getTranRef() {
        return tranRef;
    }

    public void setTranRef(@NonNull String tranRef) {
        this.tranRef = tranRef;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getCallBackUrl() {
        return callBackUrl;
    }

    public void setCallBackUrl(String callBackUrl) {
        this.callBackUrl = callBackUrl;
    }
}
