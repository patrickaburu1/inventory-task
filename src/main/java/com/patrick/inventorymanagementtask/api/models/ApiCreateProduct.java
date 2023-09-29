package com.patrick.inventorymanagementtask.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author patrick on 4/10/20
 * @project  inventory
 */
public class ApiCreateProduct {

    @NotNull
    private Product product;
    private Stock stock;
    private Supplier supplier;

    public static class Product{
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
        @JsonProperty("reOrderLevel")
        private Integer reOrderLevel;
        @JsonProperty("shopId")
        private Long shopId;

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
    }

    public static class Stock{
        @JsonProperty("buyingPrice")
        private BigDecimal buyingPrice=BigDecimal.ZERO;
        @JsonProperty("minSellingPrice")
        private BigDecimal minSellingPrice=BigDecimal.ZERO;
        @JsonProperty("currentStock")
        private Integer currentStock=0;
        @JsonProperty("sellingPrice")
        private BigDecimal sellingPrice=BigDecimal.ZERO;

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

        public Integer getCurrentStock() {
            return currentStock;
        }

        public void setCurrentStock(Integer currentStock) {
            this.currentStock = currentStock;
        }

        public BigDecimal getSellingPrice() {
            return sellingPrice;
        }

        public void setSellingPrice(BigDecimal sellingPrice) {
            this.sellingPrice = sellingPrice;
        }
    }

    public static class Supplier{
        @NotNull
        @JsonProperty("suppierId")
        private Integer suppierId;
        @JsonProperty("supplierName")
        private String supplierName;

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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
