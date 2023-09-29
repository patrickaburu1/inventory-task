package com.patrick.inventorymanagementtask.entities;

import com.patrick.inventorymanagementtask.utils.AppConstants;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author patrick on 4/20/20
 * @project inventory
 */
@Entity
@Table(name = "sales_transactions")
public class SalesTransactions  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "shop_id")
    private Long shopId;

    @Column(name = "status")
    private String status= AppConstants.ACTIVE_RECORD;

    @Column(name = "reference_no")
    private String referenceNo;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn=new Date();

    @Column(name = "voided_by")
    private Integer voidedBy;

    @Column(name = "void_reason")
    private String voidReason;

    @Column(name = "voided_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date voidedOn;

    @Transient
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getVoidedBy() {
        return voidedBy;
    }

    public void setVoidedBy(Integer voidedBy) {
        this.voidedBy = voidedBy;
    }

    public String getVoidReason() {
        return voidReason;
    }

    public void setVoidReason(String voidReason) {
        this.voidReason = voidReason;
    }

    public Date getVoidedOn() {
        return voidedOn;
    }

    public void setVoidedOn(Date voidedOn) {
        this.voidedOn = voidedOn;
    }
}
