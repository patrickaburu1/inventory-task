package com.patrick.inventorymanagementtask.api.models;

import org.springframework.lang.NonNull;

/**
 * @author patrick on 6/12/20
 * @project inventory
 */
public class ApiUpdateProfileReq {
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    private String email;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
