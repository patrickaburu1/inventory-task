package com.patrick.inventorymanagementtask.models.products;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author patrick on 9/4/19
 * @project  inventory
 */
public class ProductDetailResponse {

    private String name;
    private String code;
    private BigDecimal sellingPrice;
    private Long id;
    private BigDecimal buyingPrice;
    private BigDecimal minSp;
    private Integer reorderLevel;
    private Date expiryDate;
    private Integer categoryId;
    private String description;
    private String category;

    public String getName() {
        return name;
    }

    public ProductDetailResponse setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public ProductDetailResponse setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
        return this;
    }

    public Long getId() {
        return id;
    }

    public ProductDetailResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public BigDecimal getBuyingPrice() {
        return buyingPrice;
    }

    public ProductDetailResponse setBuyingPrice(BigDecimal buyingPrice) {
        this.buyingPrice = buyingPrice;
        return this;
    }

    public BigDecimal getMinSp() {
        return minSp;
    }

    public ProductDetailResponse setMinSp(BigDecimal minSp) {
        this.minSp = minSp;
        return this;
    }

    public Integer getReorderLevel() {
        return reorderLevel;
    }

    public ProductDetailResponse setReorderLevel(Integer reorderLevel) {
        this.reorderLevel = reorderLevel;
        return this;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public ProductDetailResponse setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public ProductDetailResponse setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductDetailResponse setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public ProductDetailResponse setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
