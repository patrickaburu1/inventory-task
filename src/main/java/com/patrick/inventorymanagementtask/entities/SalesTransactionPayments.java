package com.patrick.inventorymanagementtask.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author patrick on 5/2/20
 * @project inventory
 */
@Entity
@Table(name = "sales_transaction_payments")
public class SalesTransactionPayments implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "payment_method")
    private Long paymentMethod;

    @Column(name = "sales_transaction_id")
    private Long salesTransactionId;

    @Column(name = "flag")
    private String flag;

    @Column(name = "payment_reference_no")
    private String paymentReferenceNo;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "cash_given")
    private BigDecimal cashGiven=BigDecimal.ZERO;

    @Column(name = "cash_change")
    private BigDecimal cashChange=BigDecimal.ZERO;

    @Column(name = "created_by")
    private Integer createdBy;


    @Column(name = "shop_id")
    private Long shopId;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Long paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Long getSalesTransactionId() {
        return salesTransactionId;
    }

    public void setSalesTransactionId(Long salesTransactionId) {
        this.salesTransactionId = salesTransactionId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getPaymentReferenceNo() {
        return paymentReferenceNo;
    }

    public void setPaymentReferenceNo(String paymentReferenceNo) {
        this.paymentReferenceNo = paymentReferenceNo;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public BigDecimal getCashGiven() {
        return cashGiven;
    }

    public void setCashGiven(BigDecimal cashGiven) {
        this.cashGiven = cashGiven;
    }

    public BigDecimal getCashChange() {
        return cashChange;
    }

    public void setCashChange(BigDecimal cashChange) {
        this.cashChange = cashChange;
    }
}
