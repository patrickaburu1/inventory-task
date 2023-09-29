package com.patrick.inventorymanagementtask.models;

import javax.validation.constraints.NotNull;

/**
 * @author patrick on 6/19/19
 * @project pos
 */
public class UserRequest {
    @NotNull
    private  String firstName,lastName,phone;
    @NotNull
    private Long role;

    public String getFirstName() {
        return firstName;
    }

    public UserRequest setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserRequest setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Long getRole() {
        return role;
    }

    public UserRequest setRole(Long role) {
        this.role = role;
        return this;
    }


    public String getPhone() {
        return phone;
    }

    public UserRequest setPhone(String phone) {
        this.phone = phone;
        return this;
    }
}
