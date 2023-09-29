package com.patrick.inventorymanagementtask.models.products;

/**
 * @author patrick on 8/12/19
 * @project  inventory
 */
public class EditUserRequest {
    private Integer userId;
    private Long role;
    private String status;


    public Integer getUserId() {
        return userId;
    }

    public EditUserRequest setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }


    public Long getRole() {
        return role;
    }

    public EditUserRequest setRole(Long role) {
        this.role = role;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
