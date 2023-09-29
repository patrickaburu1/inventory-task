package com.patrick.inventorymanagementtask.models.products;

import java.math.BigDecimal;

/**
 * @author patrick on 8/12/19
 * @project shop-pos
 */
public class PaymentModeReport {
    private String name;
    private BigDecimal amount;

    public String getName() {
        return name;
    }

    public PaymentModeReport setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public PaymentModeReport setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }
}
