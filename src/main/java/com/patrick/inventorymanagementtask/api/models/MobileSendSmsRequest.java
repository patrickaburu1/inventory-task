package com.patrick.inventorymanagementtask.api.models;


import com.patrick.inventorymanagementtask.models.sms.DliteBaseSmsRequest;

import javax.validation.constraints.NotNull;

/**
 * @author patrick on 7/20/21
 * @project inventory
 */
public class MobileSendSmsRequest extends DliteBaseSmsRequest {
    @NotNull
    private Long shopId;
    public Long getShopId() {
        return shopId;
    }
    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }
}
