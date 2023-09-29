package com.patrick.inventorymanagementtask.api.models;

import java.math.BigDecimal;

/**
 * @author patrick on 5/6/20
 * @project inventory
 */
public class MigrateProductsReq {

    private String name;

    private String code;

    private String description;


    private BigDecimal sellingPrice;


    private BigDecimal buyingPrice;


    private String categoryName;
    private Long shopId;


    private Integer stock;


    private BigDecimal minSellingPrice;


    private Integer reOrderLevel=1;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public BigDecimal getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(BigDecimal buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public BigDecimal getMinSellingPrice() {
        return minSellingPrice;
    }

    public void setMinSellingPrice(BigDecimal minSellingPrice) {
        this.minSellingPrice = minSellingPrice;
    }

    public Integer getReOrderLevel() {
        return reOrderLevel;
    }

    public void setReOrderLevel(Integer reOrderLevel) {
        this.reOrderLevel = reOrderLevel;
    }
}
