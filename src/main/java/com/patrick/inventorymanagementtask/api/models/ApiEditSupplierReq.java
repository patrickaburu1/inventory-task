package com.patrick.inventorymanagementtask.api.models;

import javax.validation.constraints.NotNull;

/**
 * @author patrick on 5/18/20
 * @project inventory
 */
public class ApiEditSupplierReq {

    private Integer id;
    @NotNull
    private Long shopId;
    private String name, phone, email, address, description, mpesaNumber, bank, bankAccount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMpesaNumber() {
        return mpesaNumber;
    }

    public void setMpesaNumber(String mpesaNumber) {
        this.mpesaNumber = mpesaNumber;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }
}
