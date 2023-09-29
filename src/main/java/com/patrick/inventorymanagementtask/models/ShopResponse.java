package com.patrick.inventorymanagementtask.models;

import java.util.Date;

/**
 * @author patrick on 11/22/19
 * @project shop-pos
 */
public class ShopResponse {
    private String name;
    private Long id;
    private String location;
    private Date createdOn;
    private Date paymentDueOn;
    private String flag;
    private Boolean admin;
    private String paymentStatus;
    private String shopPref;
    private Boolean expired=false;
    private String dueOn;
    private Boolean hasProducts=false;

    public Boolean getHasProducts() {
        return hasProducts;
    }

    public void setHasProducts(Boolean hasProducts) {
        this.hasProducts = hasProducts;
    }

    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    public String getDueOn() {
        return dueOn;
    }

    public void setDueOn(String dueOn) {
        this.dueOn = dueOn;
    }

    public String getShopPref() {
        return shopPref;
    }

    public void setShopPref(String shopPref) {
        this.shopPref = shopPref;
    }

    public String getName() {
        return name;
    }

    public ShopResponse setName(String name) {
        this.name = name;
        return this;
    }

    public Long getId() {
        return id;
    }

    public ShopResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getPaymentDueOn() {
        return paymentDueOn;
    }

    public void setPaymentDueOn(Date paymentDueOn) {
        this.paymentDueOn = paymentDueOn;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
