package com.patrick.inventorymanagementtask.api.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author patrick on 5/31/20
 * @project inventory
 */
public class ApiExpenseReq {
    @NotNull
    private String name;
    private String description;
    @NotNull
    @Min(value = 1)
    private BigDecimal amount;
    @NotNull
    private Long shopId;

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }
}
