package com.patrick.inventorymanagementtask.models.sms;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author patrick on 7/20/21
 * @project myduka-pos
 */
public class DliteBaseSmsRequest {
    private List<Long> customerId;
    @NotNull
    private String message;

    public List<Long> getCustomerId() {
        return customerId;
    }

    public void setCustomerId(List<Long> customerId) {
        this.customerId = customerId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
