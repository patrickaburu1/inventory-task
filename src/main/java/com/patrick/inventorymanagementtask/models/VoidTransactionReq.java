package com.patrick.inventorymanagementtask.models;

import org.springframework.lang.NonNull;

/**
 * @author patrick on 6/28/20
 * @project myduka-pos
 */
public class VoidTransactionReq {
    @NonNull
    private Long transactionId;
    @NonNull
    private String reason;
    @NonNull
    private String password;

    @NonNull
    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(@NonNull Long transactionId) {
        this.transactionId = transactionId;
    }

    @NonNull
    public String getReason() {
        return reason;
    }

    public void setReason(@NonNull String reason) {
        this.reason = reason;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }
}
