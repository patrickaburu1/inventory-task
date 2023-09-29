package com.patrick.inventorymanagementtask.api.models;

/**
 * @author patrick on 6/12/20
 * @project inventory
 */
public class ApiShopCategoriesRes {
    private Long id;
    private String category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
