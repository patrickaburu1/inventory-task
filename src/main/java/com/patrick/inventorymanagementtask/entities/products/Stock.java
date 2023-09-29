package com.patrick.inventorymanagementtask.entities.products;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "stock")
public class Stock implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "supplier_id")
    private Long supplierId;

    @Column(name = "invoice_id")
    private Long invoiceId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "last_stock_balance")
    private Integer lastStock;

    @Column(name = "buying_price")
    private BigDecimal buyingPrice;

    @Column(name = "selling_price")
    private BigDecimal sellingPrice;

    @Column(name = "min_selling_price")
    private BigDecimal minSellingPrice;


    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Column(name = "updated_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;

    @JoinColumn(name = "product_id", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Product productLink;

    @JoinColumn(name = "supplier_id", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Suppliers supplierLink;

    @Column(name = "shop_id")
    private Long shopId;

    @Column(name = "total_cost")
    private BigDecimal totalCost;

    @Column(name = "created_by")
    private Integer createdBy;


    @JoinColumn(name = "shop_id", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Shop shopLink;

    public Long getId() {
        return id;
    }

    public Stock setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getProductId() {
        return productId;
    }

    public Stock setProductId(Long productId) {
        this.productId = productId;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Stock setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public Integer getLastStock() {
        return lastStock;
    }

    public Stock setLastStock(Integer lastStock) {
        this.lastStock = lastStock;
        return this;
    }

    public BigDecimal getBuyingPrice() {
        return buyingPrice;
    }

    public Stock setBuyingPrice(BigDecimal buyingPrice) {
        this.buyingPrice = buyingPrice;
        return this;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public Stock setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public Stock setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public Product getProductLink() {
        return productLink;
    }

    public Stock setProductLink(Product productLink) {
        this.productLink = productLink;
        return this;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public Stock setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
        return this;
    }

    public Suppliers getSupplierLink() {
        return supplierLink;
    }

    public Stock setSupplierLink(Suppliers supplierLink) {
        this.supplierLink = supplierLink;
        return this;
    }

    public Long getShopId() {
        return shopId;
    }

    public Stock setShopId(Long shopId) {
        this.shopId = shopId;
        return this;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public BigDecimal getMinSellingPrice() {
        return minSellingPrice;
    }

    public void setMinSellingPrice(BigDecimal minSellingPrice) {
        this.minSellingPrice = minSellingPrice;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }
}
