package com.patrick.inventorymanagementtask.entities.products;


import com.patrick.inventorymanagementtask.utils.AppConstants;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author patrick on 9/28/19
 * @project shopy
 */
@Entity
@Table(name = "shops")
public class Shop implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "location")
    private String location;

    @Column(name = "latitude")
    private Long latitude=0L;

    @Column(name = "longitude")
    private Long longitude=0L;

    @Column(name = "flag")
    private String flag;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "plan_id")
    private Long planId;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "payment_status")
    private String paymentStatus= AppConstants.SHOP_PAYMENT_STATUS_PAID;

    @Column(name = "trial")
    private Boolean trial=true;

    @Column(name = "payment_due_on")
    private String paymentDueOn;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn=new Date();

    @Column(name = "updated_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn=new Date();

    @Column(name = "marketer_id")
    private Long marketerId;

    @Column(name = "referral_code")
    private String referralCode;

    @Column(name = "one_off_earning")
    private Boolean onOffEarning=true;

    @Transient
    private String shopName;

    @Transient
    private Boolean hasProducts=false;

    public Boolean getHasProducts() {
        return hasProducts;
    }

    public void setHasProducts(Boolean hasProducts) {
        this.hasProducts = hasProducts;
    }

    public Long getId() {
        return id;
    }

    public Shop setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Shop setName(String name) {
        this.name = name;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Shop setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public Shop setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getFlag() {
        return flag;
    }

    public Shop setFlag(String flag) {
        this.flag = flag;
        return this;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public Shop setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public Shop setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public Shop setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Long getLatitude() {
        return latitude;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    public Long getLongitude() {
        return longitude;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Boolean getTrial() {
        return trial;
    }

    public void setTrial(Boolean trial) {
        this.trial = trial;
    }

    public String getPaymentDueOn() {
        return paymentDueOn;
    }

    public void setPaymentDueOn(String paymentDueOn) {
        this.paymentDueOn = paymentDueOn;
    }

    public String getShopName() {
        return name.substring(0,2).toUpperCase();
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getMarketerId() {
        return marketerId;
    }

    public void setMarketerId(Long marketerId) {
        this.marketerId = marketerId;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public Boolean getOnOffEarning() {
        return onOffEarning;
    }

    public void setOnOffEarning(Boolean onOffEarning) {
        this.onOffEarning = onOffEarning;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
