package com.patrick.inventorymanagementtask.models;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author patrick on 9/3/19
 * @project  inventory
 */
public class StockReport {

    private String product;
    private BigDecimal sp;
    private BigDecimal stock;
    private BigDecimal lastStock;
    private BigDecimal cost;
    private String supplier;
    private Date lastStocked;
    private Date lastSold;

    public String getProduct() {
        return product;
    }

    public StockReport setProduct(String product) {
        this.product = product;
        return this;
    }

    public BigDecimal getSp() {
        return sp;
    }

    public StockReport setSp(BigDecimal sp) {
        this.sp = sp;
        return this;
    }

    public BigDecimal getStock() {
        return stock;
    }

    public StockReport setStock(BigDecimal stock) {
        this.stock = stock;
        return this;
    }

    public BigDecimal getLastStock() {
        return lastStock;
    }

    public StockReport setLastStock(BigDecimal lastStock) {
        this.lastStock = lastStock;
        return this;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public StockReport setCost(BigDecimal cost) {
        this.cost = cost;
        return this;
    }

    public String getSupplier() {
        return supplier;
    }

    public StockReport setSupplier(String supplier) {
        this.supplier = supplier;
        return this;
    }

    public Date getLastStocked() {
        return lastStocked;
    }

    public StockReport setLastStocked(Date lastStocked) {
        this.lastStocked = lastStocked;
        return this;
    }

    public Date getLastSold() {
        return lastSold;
    }

    public StockReport setLastSold(Date lastSold) {
        this.lastSold = lastSold;
        return this;
    }
}
