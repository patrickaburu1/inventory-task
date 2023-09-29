package com.patrick.inventorymanagementtask.api.models;

import java.math.BigDecimal;

/**
 * @author patrick on 6/6/20
 * @project inventory
 */
public class ApiDebtorsRes {
    private Long id;
    private String customerPhone,name, status;
    private BigDecimal amount, balance;
    private String debtDate;
    private String givenBy;

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getDebtDate() {
        return debtDate;
    }

    public void setDebtDate(String debtDate) {
        this.debtDate = debtDate;
    }

    public String getGivenBy() {
        return givenBy;
    }

    public void setGivenBy(String givenBy) {
        this.givenBy = givenBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
