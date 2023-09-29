package com.patrick.inventorymanagementtask.models;

import java.math.BigDecimal;

/**
 * @author patrick on 3/31/20
 * @project shop-pos
 */
public class AddProductReq {
    private String name,description,code;
    private Integer category,reOrderLevel,currentStock;
    private BigDecimal buyingPrice,sellingPrice,minSellingPrice;
    private Long shopId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getReOrderLevel() {
        return reOrderLevel;
    }

    public void setReOrderLevel(Integer reOrderLevel) {
        this.reOrderLevel = reOrderLevel;
    }

    public Integer getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(Integer currentStock) {
        this.currentStock = currentStock;
    }

    public BigDecimal getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(BigDecimal buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public BigDecimal getMinSellingPrice() {
        return minSellingPrice;
    }

    public void setMinSellingPrice(BigDecimal minSellingPrice) {
        this.minSellingPrice = minSellingPrice;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }
}
