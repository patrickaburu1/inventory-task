package com.patrick.inventorymanagementtask.models.products;

import java.math.BigDecimal;

/**
 * @author patrick on 8/17/19
 * @project  inventory
 */
public class DebitRequest {
    private String type,description;
    private BigDecimal amount;

    public String getType() {
        return type;
    }

    public DebitRequest setType(String type) {
        this.type = type;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public DebitRequest setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public DebitRequest setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }
}
