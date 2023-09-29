package com.patrick.inventorymanagementtask.api.models;

import java.math.BigDecimal;

/**
 * @author patrick on 6/1/20
 * @project inventory
 */
public class ApiSalesReportRes {

    private String product;
    private Integer quantity;
    private BigDecimal amount;
    private BigDecimal total;
    private String saleDate;
    private Integer stockBalance;

    public Integer getStockBalance() {
        return stockBalance;
    }

    public void setStockBalance(Integer stockBalance) {
        this.stockBalance = stockBalance;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
