package com.patrick.inventorymanagementtask.models.products;

import java.math.BigDecimal;

public class CheckOutResponse {
    private Long id;
    private String product;
    private Integer quantity;
    private BigDecimal sellingPrice, total;

    public Long getId() {
        return id;
    }

    public CheckOutResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public String getProduct() {
        return product;
    }

    public CheckOutResponse setProduct(String product) {
        this.product = product;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public CheckOutResponse setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public CheckOutResponse setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
        return this;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public CheckOutResponse setTotal(BigDecimal total) {
        this.total = total;
        return this;
    }
}
