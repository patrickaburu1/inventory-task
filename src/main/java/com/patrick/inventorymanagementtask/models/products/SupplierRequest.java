package com.patrick.inventorymanagementtask.models.products;

/**
 * @author patrick on 9/2/19
 * @project shop-pos
 */
public class SupplierRequest {

    private Integer id;
    private String name, phone, email, address, description, mpesaNumber, bank, bankAccount;


    public Integer getId() {
        return id;
    }

    public SupplierRequest setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public SupplierRequest setName(String name) {
        this.name = name;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public SupplierRequest setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public SupplierRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public SupplierRequest setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public SupplierRequest setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getMpesaNumber() {
        return mpesaNumber;
    }

    public SupplierRequest setMpesaNumber(String mpesaNumber) {
        this.mpesaNumber = mpesaNumber;
        return this;
    }

    public String getBank() {
        return bank;
    }

    public SupplierRequest setBank(String bank) {
        this.bank = bank;
        return this;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public SupplierRequest setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
        return this;
    }
}
