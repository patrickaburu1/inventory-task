package com.patrick.inventorymanagementtask.models;

import java.math.BigDecimal;

/**
 * @author patrick on 6/26/20
 * @project myduka-pos
 */
public class ReceiptProductSoldRes {

    private String productName;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal total;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
