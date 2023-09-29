package com.patrick.inventorymanagementtask.entities.user;

import com.patrick.inventorymanagementtask.entities.products.Shop;
import com.patrick.inventorymanagementtask.utils.AppConstants;

import javax.persistence.*;
import java.util.Date;

/**
 * @author patrick on 11/22/19
 * @project  inventory
 */
@Entity
@Table(name="shop_employee_groups")
public class ShopEmployeeGroups {

    public static String SHOP_SUPER_ADMIN="SUPER_ADMIN";

    public static String SHOP_SALES_CASHIER ="CASHIER";

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="role")
    private String role;

    @Column(name="flag")
    private String flag= AppConstants.ACTIVE_RECORD;

    @Column(name = "shop_id")
    private Long shopId;

    @Column(name = "created_by")
    private int createdBy;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn=new Date();


    @JoinColumn(name = "shop_id", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Shop shopLink;

    public Long getId() {
        return id;
    }

    public ShopEmployeeGroups setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFlag() {
        return flag;
    }

    public ShopEmployeeGroups setFlag(String flag) {
        this.flag = flag;
        return this;
    }

    public String getRole() {
        return role;
    }

    public ShopEmployeeGroups setRole(String role) {
        this.role = role;
        return this;
    }

    public Long getShopId() {
        return shopId;
    }

    public ShopEmployeeGroups setShopId(Long shopId) {
        this.shopId = shopId;
        return this;
    }

    public Shop getShopLink() {
        return shopLink;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public ShopEmployeeGroups setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
}
