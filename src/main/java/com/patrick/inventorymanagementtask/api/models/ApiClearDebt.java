package com.patrick.inventorymanagementtask.api.models;

import java.math.BigDecimal;

/**
 * @author patrick on 6/6/20
 * @project inventory
 */
public class ApiClearDebt {

    private Long id, shopId;
    private BigDecimal amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
