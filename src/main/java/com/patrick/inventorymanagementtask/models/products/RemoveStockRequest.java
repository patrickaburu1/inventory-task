package com.patrick.inventorymanagementtask.models.products;

/**
 * @author patrick on 9/26/19
 * @project  inventory
 */
public class RemoveStockRequest {

    private Long productId;
    private String password;
    private Integer quantity;
    private String reason;

    public Long getProductId() {
        return productId;
    }

    public RemoveStockRequest setProductId(Long productId) {
        this.productId = productId;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RemoveStockRequest setPassword(String password) {
        this.password = password;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public RemoveStockRequest setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
