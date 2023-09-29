package com.patrick.inventorymanagementtask.entities.products;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "suppliers")
public class Suppliers implements Serializable {

    public static String SUPPLIER_DEFUALT_NAME="Un-known";

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "description")
    private String description;

    @Column(name = "mpesaPhone")
    private String mpesaPhone=null;

    @Column(name = "bank")
    private String bank=null;

    @Column(name = "bank_account")
    private String bankAccount=null;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Column(name = "updated_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;


    @Column(name = "shop_id")
    private Long shopId;

    @Column(name = "created_by")
    private Integer createdBy;

   /* @JsonIgnore
    @JoinColumn(name = "shop_id", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Shop shopLink;*/


    public Integer getId() {
        return id;
    }

    public Suppliers setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Suppliers setName(String name) {
        this.name = name;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Suppliers setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Suppliers setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Suppliers setAddress(String address) {
        this.address = address;
        return this;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public Suppliers setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public Suppliers setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Suppliers setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getMpesaPhone() {
        return mpesaPhone;
    }

    public Suppliers setMpesaPhone(String mpesaPhone) {
        this.mpesaPhone = mpesaPhone;
        return this;
    }

    public String getBank() {
        return bank;
    }

    public Suppliers setBank(String bank) {
        this.bank = bank;
        return this;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public Suppliers setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
        return this;
    }

    public Long getShopId() {
        return shopId;
    }

    public Suppliers setShopId(Long shopId) {
        this.shopId = shopId;
        return this;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }
}
