package com.patrick.inventorymanagementtask.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.math.BigDecimal;

/**
 * @author patrick on 3/31/20
 * @project shop-pos
 */
@JsonPropertyOrder({
        "ITEM",
        "ITEM DESCRIPTION",
        "CATEGORY",
        "CURRENT STOCK",
        "MIN REORDER LEVEL",
        "BUYING PRICE",
        "SELLING PRICE",
        "LAST DATE STOCKED",
        "LAST STOCKED QUANTITY",
        "LAST SUPPLIED AT",
        "LAST SUPPLIED BY"
})
public class ExportProductsReq {
    @JsonProperty("ITEM")
    private String name;
    @JsonProperty("ITEM DESCRIPTION")
    private String description;
    @JsonProperty("CATEGORY")
    private String  category;
    @JsonProperty("CURRENT STOCK")
    private Integer currentStock;
    @JsonProperty("MIN REORDER LEVEL")
    private Integer reOrderLevel;
    @JsonProperty("BUYING PRICE")
    private BigDecimal buyingPrice;
    @JsonProperty("SELLING PRICE")
    private BigDecimal sellingPrice;
    @JsonProperty("LAST DATE STOCKED")
    private String lastStockedDate;
    @JsonProperty("LAST STOCKED QUANTITY")
    private Integer lastStockedQuantity;
    @JsonProperty("LAST SUPPLIED AT")
    private BigDecimal lastSuppliedAt;
    @JsonProperty("LAST SUPPLIED BY")
    private String lastSuppliedBy;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
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

    public String getLastStockedDate() {
        return lastStockedDate;
    }

    public void setLastStockedDate(String lastStockedDate) {
        this.lastStockedDate = lastStockedDate;
    }

    public Integer getLastStockedQuantity() {
        return lastStockedQuantity;
    }

    public void setLastStockedQuantity(Integer lastStockedQuantity) {
        this.lastStockedQuantity = lastStockedQuantity;
    }

    public String getLastSuppliedBy() {
        return lastSuppliedBy;
    }

    public void setLastSuppliedBy(String lastSuppliedBy) {
        this.lastSuppliedBy = lastSuppliedBy;
    }

    public BigDecimal getLastSuppliedAt() {
        return lastSuppliedAt;
    }

    public void setLastSuppliedAt(BigDecimal lastSuppliedAt) {
        this.lastSuppliedAt = lastSuppliedAt;
    }
}
