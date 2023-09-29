package com.patrick.inventorymanagementtask.models;

/**
 * @author patrick on 6/19/19
 * @project pos
 */
public class UserVm {

    private String firstName,lastName,phone,role;
    private String status;
    private Integer id,roleId;

    public String getFirstName() {
        return firstName;
    }

    public UserVm setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserVm setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }



    public String getRole() {
        return role;
    }

    public UserVm setRole(String role) {
        this.role = role;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public UserVm setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public UserVm setRoleId(Integer roleId) {
        this.roleId = roleId;
        return this;
    }
}
