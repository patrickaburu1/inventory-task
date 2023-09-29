package com.patrick.inventorymanagementtask.api.models;

import java.util.Date;

/**
 * @author patrick on 6/13/20
 * @project inventory
 */
public class ApiShopEmployeesRes {
    private Long empoyeeId;
    private Long userGroupId;
    private String firstName,lastName,phoneNumber,status, userGroup;
    private Date createdOn;

    public Long getEmpoyeeId() {
        return empoyeeId;
    }

    public void setEmpoyeeId(Long empoyeeId) {
        this.empoyeeId = empoyeeId;
    }

    public Long getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Long userGroupId) {
        this.userGroupId = userGroupId;
    }

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
}
