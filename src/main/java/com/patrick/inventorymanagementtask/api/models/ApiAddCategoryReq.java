package com.patrick.inventorymanagementtask.api.models;

import javax.validation.constraints.NotNull;

/**
 * @author patrick on 4/6/20
 * @project shop-pos
 */
public class ApiAddCategoryReq {
    @NotNull
    private String name,description;
    @NotNull
    private Long shopId;

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

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }
}
