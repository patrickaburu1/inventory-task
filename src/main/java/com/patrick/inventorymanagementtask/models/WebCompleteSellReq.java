package com.patrick.inventorymanagementtask.models;

import java.math.BigDecimal;

/**
 * @author patrick on 5/3/20
 * @project myduka-pos
 */
public class WebCompleteSellReq {

    private String CASH,MPESA,CREDIT;
    private BigDecimal cashAmount=BigDecimal.ZERO;
    private BigDecimal mpesaAmount=BigDecimal.ZERO;
    private BigDecimal creditAmount=BigDecimal.ZERO;

    private String phone,transRef;

    public String getCASH() {
        return CASH;
    }

    public void setCASH(String CASH) {
        this.CASH = CASH;
    }

    public String getMPESA() {
        return MPESA;
    }

    public void setMPESA(String MPESA) {
        this.MPESA = MPESA;
    }

    public BigDecimal getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(BigDecimal cashAmount) {
        this.cashAmount = cashAmount;
    }

    public BigDecimal getMpesaAmount() {
        return mpesaAmount;
    }

    public void setMpesaAmount(BigDecimal mpesaAmount) {
        this.mpesaAmount = mpesaAmount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTransRef() {
        return transRef;
    }

    public void setTransRef(String transRef) {
        this.transRef = transRef;
    }

    public BigDecimal getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(BigDecimal creditAmount) {
        this.creditAmount = creditAmount;
    }

    public String getCREDIT() {
        return CREDIT;
    }

    public void setCREDIT(String CREDIT) {
        this.CREDIT = CREDIT;
    }
}
