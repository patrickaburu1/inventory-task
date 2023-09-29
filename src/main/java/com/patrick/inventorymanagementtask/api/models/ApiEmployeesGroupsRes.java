package com.patrick.inventorymanagementtask.api.models;

/**
 * @author patrick on 6/13/20
 * @project inventory
 */
public class ApiEmployeesGroupsRes {

    private Long id;
    private String group;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
