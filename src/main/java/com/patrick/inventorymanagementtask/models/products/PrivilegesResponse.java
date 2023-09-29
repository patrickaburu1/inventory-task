package com.patrick.inventorymanagementtask.models.products;

/**
 * @author patrick on 8/4/19
 * @project  inventory
 */
public class PrivilegesResponse {
    private String name,code;
    private long id;
    private Boolean value;

    public String getName() {
        return name;
    }

    public PrivilegesResponse setName(String name) {
        this.name = name;
        return this;
    }

    public String getCode() {
        return code;
    }

    public PrivilegesResponse setCode(String code) {
        this.code = code;
        return this;
    }

    public long getId() {
        return id;
    }

    public PrivilegesResponse setId(long id) {
        this.id = id;
        return this;
    }

    public Boolean getValue() {
        return value;
    }

    public PrivilegesResponse setValue(Boolean value) {
        this.value = value;
        return this;
    }
}
