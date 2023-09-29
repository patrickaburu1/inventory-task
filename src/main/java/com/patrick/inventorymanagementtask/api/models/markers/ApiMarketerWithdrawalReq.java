package com.patrick.inventorymanagementtask.api.models.markers;

import org.springframework.lang.NonNull;

import java.math.BigDecimal;

/**
 * @author patrick on 7/27/20
 * @project inventory
 */
public class ApiMarketerWithdrawalReq {

    private BigDecimal amount;
    @NonNull
    private String password;


    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount( BigDecimal amount) {
        this.amount = amount;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }
}
