package com.patrick.inventorymanagementtask.models;



import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author patrick on 4/4/20
 * @project shop-pos
 */
public class ApiAddStockReq {
    @NotNull
    private Long productId;
    private Integer supplier;
    private Long shopId;
    @NotNull
    @Min(value = 1)
    private BigDecimal buyingPrice;
    @NotNull
    @Min(value = 1)
    private BigDecimal sellingPrice;
    @NotNull
    @Min(value = 1)
    private BigDecimal minSellingPrice;
    @NotNull
    @Min(value = 1)
    private Integer quantity;


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getSupplier() {
        return supplier;
    }

    public void setSupplier(Integer supplier) {
        this.supplier = supplier;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(BigDecimal buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
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
}
