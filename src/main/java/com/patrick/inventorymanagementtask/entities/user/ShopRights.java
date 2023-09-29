package com.patrick.inventorymanagementtask.entities.user;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author patrick on 8/3/19
 * @project shop-pos
 */
@Entity
@Table(name = "shop_rights")
public class ShopRights implements Serializable {

    public static String PRIVILEGE_SELL="SELL";
    public static String PRIVILEGE_PRODUCTS="PRODUCTS";
    public static String PRIVILEGE_STOCK="STOCK";
    public static String PRIVILEGE_SUPPLIER="SUPPLIER";
    public static String PRIVILEGE_CUSTOMER="CUSTOMER";
    public static String PRIVILEGE_REPORT="REPORT";
    public static String PRIVILEGE_USERS="USERS";
    public static String PRIVILEGE_STOCKOUT="STOCKOUT";
    public static String PRIVILEGE_SETTINGS="SETTINGS";
    public static String PRIVILEGE_CASH_BOX="CASH_BOX";
    public static String PRIVILEGE_MY_EXPENSES="MY_EXPENSES";
    public static String PRIVILEGE_DEBTS="DEBTS";
    public static String PRIVILEGE_INVOICES="INVOICES";

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "default_right")
    private Boolean defaultRight;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Column(name = "updated_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;


    public Long getId() {
        return id;
    }

    public ShopRights setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }


    public ShopRights setName(String name) {
        this.name = name;
        return this;
    }

    public String getCode() {
        return code;
    }

    public ShopRights setCode(String code) {
        this.code = code;
        return this;
    }


    public Date getCreatedOn() {
        return createdOn;
    }

    public ShopRights setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public ShopRights setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public Boolean getDefaultRight() {
        return defaultRight;
    }

    public void setDefaultRight(Boolean defaultRight) {
        this.defaultRight = defaultRight;
    }
}
