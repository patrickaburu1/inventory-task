package com.patrick.inventorymanagementtask.api.models;

import org.springframework.lang.NonNull;

/**
 * @author patrick on 6/11/20
 * @project inventory
 */
public class ApiUpdateShopPaymentMethod {
    @NonNull
    private Long paymentMethodId,shopId;
    @NonNull
    private Boolean status;

    @NonNull
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(@NonNull Boolean status) {
        this.status = status;
    }

    @NonNull
    public Long getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(@NonNull Long paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    @NonNull
    public Long getShopId() {
        return shopId;
    }

    public void setShopId(@NonNull Long shopId) {
        this.shopId = shopId;
    }
}
