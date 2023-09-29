package com.patrick.inventorymanagementtask.models.products;

import java.math.BigDecimal;
import java.math.BigInteger;

public class SalesReportReponse  {
    private String name;
    private BigInteger quantity;
    private BigDecimal sp, sale,profit;

    public String getName() {
        return name;
    }

    public SalesReportReponse setName(String name) {
        this.name = name;
        return this;
    }

    public BigInteger getQuantity() {
        return quantity;
    }

    public SalesReportReponse setQuantity(BigInteger quantity) {
        this.quantity = quantity;
        return this;
    }

    public BigDecimal getSp() {
        return sp;
    }

    public SalesReportReponse setSp(BigDecimal sp) {
        this.sp = sp;
        return this;
    }

    public BigDecimal getSale() {
        return sale;
    }

    public SalesReportReponse setSale(BigDecimal sale) {
        this.sale = sale;
        return this;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public SalesReportReponse setProfit(BigDecimal profit) {
        this.profit = profit;
        return this;
    }
}
