package com.patrick.inventorymanagementtask.models.sms;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author patrick on 7/22/21
 * @project myduka-pos
 */
public class ToupSmsUnitsRequestWeb {
    @NotNull
    private String phoneNumber;
    @NotNull
    private BigDecimal amount;


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
