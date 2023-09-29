package com.patrick.inventorymanagementtask.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author patrick on 4/12/20
 * @project inventory
 */
public class ApiAddStockReq {

    @NotNull
    private Long id;
    @NotNull
    @JsonProperty("shopId")
    private Long shopId;
    @NotNull
    @JsonProperty("buyingPrice")
    private BigDecimal buyingPrice=BigDecimal.ZERO;
    @NotNull
    @JsonProperty("minSellingPrice")
    private BigDecimal minSellingPrice=BigDecimal.ZERO;
    @NotNull
    @JsonProperty("sellingPrice")
    private BigDecimal sellingPrice=BigDecimal.ZERO;
    @NotNull
    @Min(value = 1)
    private Integer quantity;

    @NotNull
    @JsonProperty("suppierId")
    private Integer suppierId;
    @JsonProperty("supplierName")
    private String supplierName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public BigDecimal getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(BigDecimal buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public BigDecimal getMinSellingPrice() {
        return minSellingPrice;
    }

    public void setMinSellingPrice(BigDecimal minSellingPrice) {
        this.minSellingPrice = minSellingPrice;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getSuppierId() {
        return suppierId;
    }

    public void setSuppierId(Integer suppierId) {
        this.suppierId = suppierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
}
