package com.patrick.inventorymanagementtask.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author patrick on 4/11/20
 * @project inventory
 */
public class ApiEditProductReq {
    @NotNull
    private Long id;
    @NotNull
    @JsonProperty("categoryId")
    private Integer categoryId=0;
    @JsonProperty("categoryName")
    private String categoryName;
    @NotNull
    @JsonProperty("name")
    private String name;
    @JsonProperty("code")
    private String code;
    @JsonProperty("description")
    private String description;
    @NotNull
    @JsonProperty("reOrderLevel")
    private Integer reOrderLevel;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

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

    public Integer getReOrderLevel() {
        return reOrderLevel;
    }

    public void setReOrderLevel(Integer reOrderLevel) {
        this.reOrderLevel = reOrderLevel;
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
}
