package com.patrick.inventorymanagementtask.models.products;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author patrick on 8/17/19
 * @project  inventory
 */
public class DebtorsResponse {

    private Long checkOutId;
    private BigDecimal amount;
    private Date date;
    private Date dateCleared;
    private String customerName;
    private String customerPhone;
    private Boolean status=false;
    private String givenBy;

    public Long getCheckOutId() {
        return checkOutId;
    }

    public DebtorsResponse setCheckOutId(Long checkOutId) {
        this.checkOutId = checkOutId;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public DebtorsResponse setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public DebtorsResponse setDate(Date date) {
        this.date = date;
        return this;
    }

    public String getCustomerName() {
        return customerName;
    }

    public DebtorsResponse setCustomerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public DebtorsResponse setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
        return this;
    }

    public Boolean getStatus() {
        return status;
    }

    public DebtorsResponse setStatus(Boolean status) {
        this.status = status;
        return this;
    }

    public Date getDateCleared() {
        return dateCleared;
    }

    public DebtorsResponse setDateCleared(Date dateCleared) {
        this.dateCleared = dateCleared;
        return this;
    }

    public String getGivenBy() {
        return givenBy;
    }

    public DebtorsResponse setGivenBy(String givenBy) {
        this.givenBy = givenBy;
        return this;
    }
}
