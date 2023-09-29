package com.patrick.inventorymanagementtask.models;

import java.math.BigDecimal;

/**
 * @author patrick on 6/26/20
 * @project myduka-pos
 */
public class ReceiptPaymentMethods {

    private String name;
    private BigDecimal amount;
    private String paymentReferenceNo;
    private BigDecimal change;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPaymentReferenceNo() {
        return paymentReferenceNo;
    }

    public void setPaymentReferenceNo(String paymentReferenceNo) {
        this.paymentReferenceNo = paymentReferenceNo;
    }

    public BigDecimal getChange() {
        return change;
    }

    public void setChange(BigDecimal change) {
        this.change = change;
    }
}
