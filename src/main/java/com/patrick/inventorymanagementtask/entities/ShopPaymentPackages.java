package com.patrick.inventorymanagementtask.entities;

import com.patrick.inventorymanagementtask.utils.AppConstants;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author patrick on 11/27/19
 * @project shop-pos
 */
@Entity
@Table(name = "shop_payment_packages")
public class ShopPaymentPackages implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;


    @Column(name = "trial_days")
    private Integer trialDays;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Column(name = "color_code")
    private String colorCode;

    @Column(name = "max_no_of_employees")
    private Integer maxNoOfEmployees;

    @Column(name = "is_free")
    private Boolean trial;

    @Column(name = "flag")
    private String flag= AppConstants.ACTIVE_RECORD;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Column(name = "updated_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;

    @Transient
    private Boolean currentPlan=false;

    public Boolean getCurrentPlan() {
        return currentPlan;
    }

    public void setCurrentPlan(Boolean currentPlan) {
        this.currentPlan = currentPlan;
    }

    public Long getId() {
        return id;
    }

    public ShopPaymentPackages setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ShopPaymentPackages setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ShopPaymentPackages setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getTrialDays() {
        return trialDays;
    }

    public void setTrialDays(Integer trialDays) {
        this.trialDays = trialDays;
    }

    public Boolean getTrial() {
        return trial;
    }

    public void setTrial(Boolean trial) {
        this.trial = trial;
    }

    public String getFlag() {
        return flag;
    }

    public ShopPaymentPackages setFlag(String flag) {
        this.flag = flag;
        return this;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public ShopPaymentPackages setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public ShopPaymentPackages setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public Integer getMaxNoOfEmployees() {
        return maxNoOfEmployees;
    }

    public void setMaxNoOfEmployees(Integer maxNoOfEmployees) {
        this.maxNoOfEmployees = maxNoOfEmployees;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
}
