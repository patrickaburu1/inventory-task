package com.patrick.inventorymanagementtask.entities.user;

import com.patrick.inventorymanagementtask.utils.AppConstants;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author patrick on 6/19/19
 * @project pos
 */
@Entity
@Table(name ="shop_employees" )
public class ShopEmployees implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "shop_id")
    private Long shopId;

    @Column(name = "active")
    private String active= AppConstants.ACTIVE_RECORD;

    @Column(name = "default_shop")
    private Boolean defaultShop=false;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createOn=new Date();

    @Column(name = "updated_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;

    @JoinColumn(name ="role_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private ShopEmployeeGroups shopEmployeeGroupsLink;


    @JoinColumn(name ="user_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Users userLink;

    public Long getId() {
        return id;
    }

    public ShopEmployees setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getRoleId() {
        return roleId;
    }

    public ShopEmployees setRoleId(Long roleId) {
        this.roleId = roleId;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public ShopEmployees setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public ShopEmployeeGroups getShopEmployeeGroupsLink() {
        return shopEmployeeGroupsLink;
    }

    public Long getShopId() {
        return shopId;
    }

    public ShopEmployees setShopId(Long shopId) {
        this.shopId = shopId;
        return this;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Boolean getDefaultShop() {
        return defaultShop;
    }

    public void setDefaultShop(Boolean defaultShop) {
        this.defaultShop = defaultShop;
    }

    public Users getUserLink() {
        return userLink;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getCreateOn() {
        return createOn;
    }

    public void setCreateOn(Date createOn) {
        this.createOn = createOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }
}
