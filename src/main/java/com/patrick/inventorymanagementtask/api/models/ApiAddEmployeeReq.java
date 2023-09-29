package com.patrick.inventorymanagementtask.api.models;

import org.springframework.lang.NonNull;

import javax.validation.constraints.NotNull;

/**
 * @author patrick on 6/13/20
 * @project inventory
 */
public class ApiAddEmployeeReq {
    @NotNull
    private  String phone;
    @NonNull
    private  String firstName,lastName;
    @NotNull
    private Long groupId;
    @NonNull
    private Long shopId;


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @NonNull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NonNull String firstName) {
        this.firstName = firstName;
    }

    @NonNull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(@NonNull String lastName) {
        this.lastName = lastName;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    @NonNull
    public Long getShopId() {
        return shopId;
    }

    public void setShopId(@NonNull Long shopId) {
        this.shopId = shopId;
    }
}
