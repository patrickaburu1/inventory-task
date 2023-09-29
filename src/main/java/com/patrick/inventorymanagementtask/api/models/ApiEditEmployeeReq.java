package com.patrick.inventorymanagementtask.api.models;

import org.springframework.lang.NonNull;

/**
 * @author patrick on 6/13/20
 * @project inventory
 */
public class ApiEditEmployeeReq {
    @NonNull
    private Long employeeId, shopId;
    @NonNull
    private String status;
    @NonNull
    private Long groupId;

    @NonNull
    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(@NonNull Long employeeId) {
        this.employeeId = employeeId;
    }

    @NonNull
    public Long getShopId() {
        return shopId;
    }

    public void setShopId(@NonNull Long shopId) {
        this.shopId = shopId;
    }

    @NonNull
    public String getStatus() {
        return status;
    }

    public void setStatus(@NonNull String status) {
        this.status = status;
    }

    @NonNull
    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(@NonNull Long groupId) {
        this.groupId = groupId;
    }
}
