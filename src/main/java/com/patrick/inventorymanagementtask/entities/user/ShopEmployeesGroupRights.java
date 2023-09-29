package com.patrick.inventorymanagementtask.entities.user;

import com.patrick.inventorymanagementtask.entities.products.Shop;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author patrick on 8/7/19
 * @project shop-pos
 */
@Entity
@Table(name = "shop_employees_group_rights")
public class ShopEmployeesGroupRights implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "privilege_id")
    private Long privilegeId;

    @Column(name = "created_by")
    private int createdBy;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Column(name = "updated_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;

    @Column(name = "shop_id")
    private Long shopId;

    @JoinColumn(name = "shop_id", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Shop shopLink;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public ShopEmployeesGroupRights setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getPrivilegeId() {
        return privilegeId;
    }

    public ShopEmployeesGroupRights setPrivilegeId(Long privilegeId) {
        this.privilegeId = privilegeId;
        return this;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public ShopEmployeesGroupRights setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Long getRoleId() {
        return roleId;
    }

    public ShopEmployeesGroupRights setRoleId(Long roleId) {
        this.roleId = roleId;
        return this;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public ShopEmployeesGroupRights setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public ShopEmployeesGroupRights setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public Long getShopId() {
        return shopId;
    }

    public ShopEmployeesGroupRights setShopId(Long shopId) {
        this.shopId = shopId;
        return this;
    }

    public Shop getShopLink() {
        return shopLink;
    }
}
