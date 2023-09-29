package com.patrick.inventorymanagementtask.models;

import org.springframework.lang.NonNull;

import java.math.BigDecimal;

/**
 * @author patrick on 6/23/20
 * @project myduka-pos
 */
public class AddToCartReq {

    @NonNull
    private Integer quantity;
    @NonNull
    private Long productId;
    @NonNull
    private BigDecimal sp;

    @NonNull
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(@NonNull Integer quantity) {
        this.quantity = quantity;
    }

    @NonNull
    public Long getProductId() {
        return productId;
    }

    public void setProductId(@NonNull Long productId) {
        this.productId = productId;
    }

    @NonNull
    public BigDecimal getSp() {
        return sp;
    }

    public void setSp(@NonNull BigDecimal sp) {
        this.sp = sp;
    }
}
