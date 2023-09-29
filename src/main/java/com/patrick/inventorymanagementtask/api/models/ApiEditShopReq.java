package com.patrick.inventorymanagementtask.api.models;

import javax.validation.constraints.NotNull;

/**
 * @author patrick on 11/15/21
 * @project inventory
 */
public class ApiEditShopReq {
    @NotNull
    private Long shopId;
    @NotNull
    private String name;
    private String description;
    private String  locationName;
    private AppCreateShopReq.Location location;
    @NotNull
    private Long categoryId;

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public AppCreateShopReq.Location getLocation() {
        return location;
    }

    public void setLocation(AppCreateShopReq.Location location) {
        this.location = location;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
