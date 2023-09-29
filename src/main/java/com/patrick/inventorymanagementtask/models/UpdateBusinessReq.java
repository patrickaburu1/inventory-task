package com.patrick.inventorymanagementtask.models;

import org.springframework.lang.NonNull;

/**
 * @author patrick on 6/26/20
 * @project myduka-pos
 */
public class UpdateBusinessReq {
    @NonNull
    private String businessName, businessPhoneNumber, businessAddress;
    @NonNull
    private Long category;

    @NonNull
    public Long getCategory() {
        return category;
    }

    public void setCategory(@NonNull Long category) {
        this.category = category;
    }

    private String businessEmail;
    @NonNull
    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(@NonNull String businessName) {
        this.businessName = businessName;
    }

    @NonNull
    public String getBusinessPhoneNumber() {
        return businessPhoneNumber;
    }

    public void setBusinessPhoneNumber(@NonNull String businessPhoneNumber) {
        this.businessPhoneNumber = businessPhoneNumber;
    }



    @NonNull
    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(@NonNull String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getBusinessEmail() {
        return businessEmail;
    }

    public void setBusinessEmail(String businessEmail) {
        this.businessEmail = businessEmail;
    }
}
