package com.patrick.inventorymanagementtask.models.products;

import java.math.BigDecimal;

public class Top10ProductReponse {

   private String product;
   private BigDecimal revenue;

    public String getProduct() {
        return product;
    }

    public Top10ProductReponse setProduct(String product) {
        this.product = product;
        return this;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }

    public Top10ProductReponse setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
        return this;
    }
}
