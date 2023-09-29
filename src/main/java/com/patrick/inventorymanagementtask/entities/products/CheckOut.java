package com.patrick.inventorymanagementtask.entities.products;

import com.patrick.inventorymanagementtask.entities.user.Users;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "checkout")
public class CheckOut implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "flag")
    private String flag;

    @Column(name = "created_by")
    private int createdBy;

    @Column(name = "receipt_url")
    private String receiptUrl;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Column(name = "updated_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;

    @JoinColumn(name = "customer_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customerLink;

    @JoinColumn(name = "created_by", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Users usersLink;

    @Column(name = "shop_id")
    private Long shopId;

    @JoinColumn(name = "shop_id", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Shop shopLink;

    public Long getId() {
        return id;
    }

    public CheckOut setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFlag() {
        return flag;
    }

    public CheckOut setFlag(String flag) {
        this.flag = flag;
        return this;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public CheckOut setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public CheckOut setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public CheckOut setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public String getReceiptUrl() {
        return receiptUrl;
    }

    public CheckOut setReceiptUrl(String receiptUrl) {
        this.receiptUrl = receiptUrl;
        return this;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public CheckOut setCustomerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public Customer getCustomerLink() {
        return customerLink;
    }

    public CheckOut setCustomerLink(Customer customerLink) {
        this.customerLink = customerLink;
        return this;
    }

    public Users getUsersLink() {
        return usersLink;
    }

    public CheckOut setUsersLink(Users usersLink) {
        this.usersLink = usersLink;
        return this;
    }

    public Long getShopId() {
        return shopId;
    }

    public CheckOut setShopId(Long shopId) {
        this.shopId = shopId;
        return this;
    }

    public Shop getShopLink() {
        return shopLink;
    }
}
