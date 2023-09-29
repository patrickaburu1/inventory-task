package com.patrick.inventorymanagementtask.entities.products;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "sales")
public class Sales implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "product")
    private Long productId;

    @Column(name = "selling_price")
    private BigDecimal sellingPrice;

    @Column(name = "buying_price")
    private BigDecimal buyingPrice;

    @Column(name = "min_selling_price")
    private BigDecimal minSellingPrice;

    @Column(name = "sold_at")
    private BigDecimal soldAt;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "profit_per_product")
    private BigDecimal profitPerProduct;

    @Column(name = "total_profit")
    private BigDecimal totalProfit;

    @Column(name = "created_by")
    private int createdBy;

    @Column(name = "flag")
    private String flag;

    @Column(name = "payment_mode")
    private Long paymentMode;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Column(name = "updated_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;

    @Column(name = "shop_id")
    private Long shopId;

    @Column(name = "sales_transaction_id")
    private Long salesTransactionId;


    @JoinColumn(name = "product", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Product productLink;

    @JoinColumn(name = "payment_mode", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PaymentMethods paymentMethodsLink;

    @JoinColumn(name = "shop_id", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Shop shopLink;

    public Long getId() {
        return id;
    }

    public Sales setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getProductId() {
        return productId;
    }

    public Sales setProductId(Long productId) {
        this.productId = productId;
        return this;
    }


    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public Sales setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Sales setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public Sales setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public Sales setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public String getFlag() {
        return flag;
    }

    public Sales setFlag(String flag) {
        this.flag = flag;
        return this;
    }

    public Long getPaymentMode() {
        return paymentMode;
    }

    public Sales setPaymentMode(Long paymentMode) {
        this.paymentMode = paymentMode;
        return this;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public Sales setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public Sales setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public Product getProductLink() {
        return productLink;
    }

    public Sales setProductLink(Product productLink) {
        this.productLink = productLink;
        return this;
    }

    public Long getSalesTransactionId() {
        return salesTransactionId;
    }

    public void setSalesTransactionId(Long salesTransactionId) {
        this.salesTransactionId = salesTransactionId;
    }

    public BigDecimal getProfitPerProduct() {
        return profitPerProduct;
    }

    public Sales setProfitPerProduct(BigDecimal profitPerProduct) {
        this.profitPerProduct = profitPerProduct;
        return this;
    }

    public BigDecimal getTotalProfit() {
        return totalProfit;
    }

    public Sales setTotalProfit(BigDecimal totalProfit) {
        this.totalProfit = totalProfit;
        return this;
    }

    public PaymentMethods getPaymentMethodsLink() {
        return paymentMethodsLink;
    }

    public Sales setPaymentMethodsLink(PaymentMethods paymentMethodsLink) {
        this.paymentMethodsLink = paymentMethodsLink;
        return this;
    }

    public Long getShopId() {
        return shopId;
    }

    public Sales setShopId(Long shopId) {
        this.shopId = shopId;
        return this;
    }

    public BigDecimal getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(BigDecimal buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public BigDecimal getMinSellingPrice() {
        return minSellingPrice;
    }

    public void setMinSellingPrice(BigDecimal minSellingPrice) {
        this.minSellingPrice = minSellingPrice;
    }

    public BigDecimal getSoldAt() {
        return soldAt;
    }

    public void setSoldAt(BigDecimal soldAt) {
        this.soldAt = soldAt;
    }
}
