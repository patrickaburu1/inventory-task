package com.patrick.inventorymanagementtask.entities.marketers;

import com.patrick.inventorymanagementtask.utils.AppConstants;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author patrick on 7/27/20
 * @project inventory
 */
@Entity
@Table(name = "marketers_withdrawals_requests")
public class MarketerWithdrawalsRequests implements Serializable {
    public static String WITHDRAWAL_PENDING="PENDING";
    public static String WITHDRAWAL_APPROVED="APPROVED";
    public static String WITHDRAWAL_REJECTED="REJECTED";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "flag")
    private String flag= AppConstants.ACTIVE_RECORD;

    @Column(name = "marketer_id")
    private Long marketerId;

    @Column(name = "status")
    private String status=WITHDRAWAL_PENDING;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "actioned_by")
    private Integer actionedBy;

    @Column(name = "transaction_id")
    private Long transactionId;

    @Column(name = "action_reason")
    private String actionReason;

    @Column(name = "reference_no")
    private String referenceNo;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn=new Date();

    @Column(name = "updated_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Long getMarketerId() {
        return marketerId;
    }

    public void setMarketerId(Long marketerId) {
        this.marketerId = marketerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getActionedBy() {
        return actionedBy;
    }

    public void setActionedBy(Integer actionedBy) {
        this.actionedBy = actionedBy;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getActionReason() {
        return actionReason;
    }

    public void setActionReason(String actionReason) {
        this.actionReason = actionReason;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }
}
