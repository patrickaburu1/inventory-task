package com.patrick.inventorymanagementtask.entities.promos;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author patrick on 11/21/21
 * @project inventory
 */
@Table(name = "promotions_shop_entries")
@Entity
public class PromotionsShopEntries implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "shop_id")
    private Long shopId;

    @Column(name = "status")
    private String status;

    @Column(name = "promotion_id")
    private Long promotionId;

    @Column(name = "is_redeemed")
    private Boolean redeemed;

    @Column(name = "redeemed_by")
    private Integer redeemedBy;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "promo_days")
    private Integer promoDays;

    @Column(name = "payment_entry_id")
    private Long  paymentEntryId;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public Integer getRedeemedBy() {
        return redeemedBy;
    }

    public void setRedeemedBy(Integer redeemedBy) {
        this.redeemedBy = redeemedBy;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getPromoDays() {
        return promoDays;
    }

    public void setPromoDays(Integer promoDays) {
        this.promoDays = promoDays;
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

    public Boolean getRedeemed() {
        return redeemed;
    }

    public void setRedeemed(Boolean redeemed) {
        this.redeemed = redeemed;
    }

    public Long getPaymentEntryId() {
        return paymentEntryId;
    }

    public void setPaymentEntryId(Long paymentEntryId) {
        this.paymentEntryId = paymentEntryId;
    }
}
