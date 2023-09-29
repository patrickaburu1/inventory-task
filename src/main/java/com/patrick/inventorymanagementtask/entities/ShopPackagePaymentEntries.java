package com.patrick.inventorymanagementtask.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author patrick on 5/1/20
 * @project inventory
 */
@Entity
@Table(name = "shop_package_payment_entries")
public class ShopPackagePaymentEntries implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "shop_id")
    private Long shopId;

    @Column(name = "plan_id")
    private Long planId;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "reference_no")
    private String referenceNo;

    @Column(name = "status")
    private String status="Pending";

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "last_due")
    private String lastDue;

    @Column(name = "next_due_on")
    private String nextDueOn;

    @Column(name = "request_id")
    private String requestId;

    @Column(name = "months_renewed")
    private Integer monthsRenewed;

    @Column(name = "days_before_due")
    private Integer daysBeforeDue;

    @Column(name = "days_added")
    private Integer daysAdded;

    @Column(name = "promo_code")
    private String promoCode;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn=new Date();

    @Column(name = "updated_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn=new Date();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getLastDue() {
        return lastDue;
    }

    public void setLastDue(String lastDue) {
        this.lastDue = lastDue;
    }

    public String getNextDueOn() {
        return nextDueOn;
    }

    public void setNextDueOn(String nextDueOn) {
        this.nextDueOn = nextDueOn;
    }

    public Integer getMonthsRenewed() {
        return monthsRenewed;
    }

    public void setMonthsRenewed(Integer monthsRenewed) {
        this.monthsRenewed = monthsRenewed;
    }

    public Integer getDaysBeforeDue() {
        return daysBeforeDue;
    }

    public void setDaysBeforeDue(Integer daysBeforeDue) {
        this.daysBeforeDue = daysBeforeDue;
    }

    public Integer getDaysAdded() {
        return daysAdded;
    }

    public void setDaysAdded(Integer daysAdded) {
        this.daysAdded = daysAdded;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }
}
