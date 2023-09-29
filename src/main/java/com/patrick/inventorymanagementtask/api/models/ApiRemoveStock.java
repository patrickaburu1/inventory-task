package com.patrick.inventorymanagementtask.api.models;

import javax.validation.constraints.NotNull;

/**
 * @author patrick on 4/13/20
 * @project inventory
 */
public class ApiRemoveStock {
    @NotNull
    private Long productId;
    private String password;
    private Integer quantity=0;
    @NotNull
    private Long shopId;
    private String reason;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
