package com.patrick.inventorymanagementtask.models.products;

import java.math.BigDecimal;

/**
 * @author patrick on 7/25/19
 * @project pos
 */
public class ReceiptList {
   private String item;
   private Integer quantity;
    private   BigDecimal amount;

    public String getItem() {
        return item;
    }

    public ReceiptList setItem(String item) {
        this.item = item;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public ReceiptList setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public ReceiptList setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }
}
