package com.patrick.inventorymanagementtask.models.products;

import com.sun.istack.NotNull;


/**
 * @author patrick on 8/19/19
 * @project  inventory
 */
public class CustomerRequest {

    private Long id;

    @NotNull
    private String name,phone;
    private String description;

    public String getName() {
        return name;
    }

    public CustomerRequest setName(String name) {
        this.name = name;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public CustomerRequest setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CustomerRequest setDescription(String description) {
        this.description = description;
        return this;
    }

    public Long getId() {
        return id;
    }

    public CustomerRequest setId(Long id) {
        this.id = id;
        return this;
    }
}
