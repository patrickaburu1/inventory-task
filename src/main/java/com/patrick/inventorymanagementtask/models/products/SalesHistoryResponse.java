package com.patrick.inventorymanagementtask.models.products;

import java.math.BigDecimal;
import java.util.Date;

public class SalesHistoryResponse {
    private String name;
    private Integer quantity;
    private BigDecimal bp,sp, sale,profit, soldAt;
    private Date date;

    public String getName() {
        return name;
    }

    public SalesHistoryResponse setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public SalesHistoryResponse setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public BigDecimal getSp() {
        return sp;
    }

    public SalesHistoryResponse setSp(BigDecimal sp) {
        this.sp = sp;
        return this;
    }

    public BigDecimal getSale() {
        return sale;
    }

    public SalesHistoryResponse setSale(BigDecimal sale) {
        this.sale = sale;
        return this;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public SalesHistoryResponse setProfit(BigDecimal profit) {
        this.profit = profit;
        return this;
    }

    public BigDecimal getBp() {
        return bp;
    }

    public SalesHistoryResponse setBp(BigDecimal bp) {
        this.bp = bp;
        return this;
    }

    public BigDecimal getSoldAt() {
        return soldAt;
    }

    public SalesHistoryResponse setSoldAt(BigDecimal soldAt) {
        this.soldAt = soldAt;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public SalesHistoryResponse setDate(Date date) {
        this.date = date;
        return this;
    }
}
