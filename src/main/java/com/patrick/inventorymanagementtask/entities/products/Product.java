package com.patrick.inventorymanagementtask.entities.products;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.patrick.inventorymanagementtask.utils.AppConstants;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "products")
public class Product implements Serializable {
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

    @Column(name = "flag")
    private String flag = AppConstants.ACTIVE_RECORD;

    @Column(name = "description")
    private String description;

    @Column(name = "selling_price")
    private BigDecimal sellingPrice;

    @Column(name = "buying_price")
    private BigDecimal buyingPrice;

    @Column(name = "category")
    private Integer categoryId;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "min_selling_price")
    private BigDecimal minSellingPrice = new BigDecimal(50);

    @Column(name = "re_order_level")
    private Integer reOrderLevel = 10;

    @JsonIgnore
    @Column(name = "expiry_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiryDate = new Date();

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn = new Date();

    @Column(name = "updated_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn = new Date();

    @Transient
    private String categoryName;

    @JsonIgnore
    @JoinColumn(name = "category", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Category categoryLink;

    @Column(name = "shop_id")
    private Long shopId;


    /*  @JoinColumn(name = "shop_id", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Shop shopLink;*/

    public Long getId() {
        return id;
    }

    public Product setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public String getCode() {
        return code;
    }

    public Product setCode(String code) {
        this.code = code;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Product setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public Product setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
        return this;
    }

    public BigDecimal getBuyingPrice() {
        return buyingPrice;
    }

    public Product setBuyingPrice(BigDecimal buyingPrice) {
        this.buyingPrice = buyingPrice;
        return this;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public Product setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public Product setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public Product setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public Integer getStock() {
        return stock;
    }

    public Product setStock(Integer stock) {
        this.stock = stock;
        return this;
    }

    public BigDecimal getMinSellingPrice() {
        return minSellingPrice;
    }

    public Product setMinSellingPrice(BigDecimal minSellingPrice) {
        this.minSellingPrice = minSellingPrice;
        return this;
    }

    public Integer getReOrderLevel() {
        return reOrderLevel;
    }

    public Product setReOrderLevel(Integer reOrderLevel) {
        this.reOrderLevel = reOrderLevel;
        return this;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public Product setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public Category getCategoryLink() {
        return categoryLink;
    }

    public String getFlag() {
        return flag;
    }

    public Product setFlag(String flag) {
        this.flag = flag;
        return this;
    }

    public Long getShopId() {
        return shopId;
    }

    public Product setShopId(Long shopId) {
        this.shopId = shopId;
        return this;
    }

    public String getCategoryName() {
        return categoryLink.getName();
    }
}

