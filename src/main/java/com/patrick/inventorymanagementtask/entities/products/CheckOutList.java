package com.patrick.inventorymanagementtask.entities.products;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "checkout_list")
public class CheckOutList implements Serializable {
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

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "check_out_id")
    private Long checkOutId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "selling_price")
    private BigDecimal sellingPrice;

    @Column(name = "total")
    private BigDecimal total;


    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Column(name = "updated_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;

    @JoinColumn(name = "product_id", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Product productLink;

    @Column(name = "shop_id")
    private Long shopId;

    @JoinColumn(name = "shop_id", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Shop shopLink;

    public Long getId() {
        return id;
    }

    public CheckOutList setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFlag() {
        return flag;
    }

    public CheckOutList setFlag(String flag) {
        this.flag = flag;
        return this;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public CheckOutList setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Long getProductId() {
        return productId;
    }

    public CheckOutList setProductId(Long productId) {
        this.productId = productId;
        return this;
    }

    public Long getCheckOutId() {
        return checkOutId;
    }

    public CheckOutList setCheckOutId(Long checkOutId) {
        this.checkOutId = checkOutId;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public CheckOutList setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public CheckOutList setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
        return this;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public CheckOutList setTotal(BigDecimal total) {
        this.total = total;
        return this;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public CheckOutList setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public CheckOutList setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public Product getProductLink() {
        return productLink;
    }

    public CheckOutList setProductLink(Product productLink) {
        this.productLink = productLink;
        return this;
    }

    public Long getShopId() {
        return shopId;
    }

    public CheckOutList setShopId(Long shopId) {
        this.shopId = shopId;
        return this;
    }

    public Shop getShopLink() {
        return shopLink;
    }
}
