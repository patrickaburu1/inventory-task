package com.patrick.inventorymanagementtask.api.models;

/**
 * @author patrick on 5/5/20
 * @project  inventory
 */
public class MCategoriesReq {
    private String name,description;
    private Long shopId;

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
}
