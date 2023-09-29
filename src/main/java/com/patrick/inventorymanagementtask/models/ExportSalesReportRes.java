package com.patrick.inventorymanagementtask.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.math.BigDecimal;

/**
 * @author patrick on 10/1/21
 * @project myduka-pos
 */
@JsonPropertyOrder({
        "ITEM",
        "QUANTITY",
        "SOLD AT",
        "TOTAL AMOUNT",
        "SOLD ON",
        "Current Stock"
})
public class ExportSalesReportRes {
    @JsonProperty(value = "ITEM")
    private String product;
    @JsonProperty("QUANTITY")
    private Integer quantity;
    @JsonProperty("SOLD AT")
    private BigDecimal amount;
    @JsonProperty("TOTAL AMOUNT")
    private BigDecimal total;
    @JsonProperty("SOLD ON")
    private String saleDate;
    @JsonProperty("CURRENT STOCK")
    private Integer currentStock;

    public Integer getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(Integer currentStock) {
        this.currentStock = currentStock;
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

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }
}
