package com.patrick.inventorymanagementtask.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author patrick on 5/1/20
 * @project myduka-pos
 */
public class CallBackResponseRenewFlutterWave {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("txRef")
    private String txRef;
    @JsonProperty("flwRef")
    private String flwRef;
    @JsonProperty("orderRef")
    private String orderRef;
    @JsonProperty("paymentPlan")
    private String paymentPlan;
    @JsonProperty("paymentPage")
    private String paymentPage;
    @JsonProperty("createdAt")
    private String createdAt;
    @JsonProperty("amount")
    private Integer amount;
    @JsonProperty("charged_amount")
    private Integer chargedAmount;
    @JsonProperty("status")
    private String status;
    @JsonProperty("IP")
    private String iP;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("appfee")
    private Double appfee;
    @JsonProperty("merchantfee")
    private Integer merchantfee;
    @JsonProperty("merchantbearsfee")
    private Integer merchantbearsfee;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTxRef() {
        return txRef;
    }

    public void setTxRef(String txRef) {
        this.txRef = txRef;
    }

    public String getFlwRef() {
        return flwRef;
    }

    public void setFlwRef(String flwRef) {
        this.flwRef = flwRef;
    }

    public String getOrderRef() {
        return orderRef;
    }

    public void setOrderRef(String orderRef) {
        this.orderRef = orderRef;
    }

    public String getPaymentPlan() {
        return paymentPlan;
    }

    public void setPaymentPlan(String paymentPlan) {
        this.paymentPlan = paymentPlan;
    }

    public String getPaymentPage() {
        return paymentPage;
    }

    public void setPaymentPage(String paymentPage) {
        this.paymentPage = paymentPage;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getChargedAmount() {
        return chargedAmount;
    }

    public void setChargedAmount(Integer chargedAmount) {
        this.chargedAmount = chargedAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getiP() {
        return iP;
    }

    public void setiP(String iP) {
        this.iP = iP;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getAppfee() {
        return appfee;
    }

    public void setAppfee(Double appfee) {
        this.appfee = appfee;
    }

    public Integer getMerchantfee() {
        return merchantfee;
    }

    public void setMerchantfee(Integer merchantfee) {
        this.merchantfee = merchantfee;
    }

    public Integer getMerchantbearsfee() {
        return merchantbearsfee;
    }

    public void setMerchantbearsfee(Integer merchantbearsfee) {
        this.merchantbearsfee = merchantbearsfee;
    }
}
