package com.patrick.inventorymanagementtask.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author patrick on 5/1/20
 * @project inventory
 */
@Entity
@Table(name = "product_price_trail")
public class ProductPriceTrail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "shop_id")
    private Long shopId;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "buying_price")
    private BigDecimal buyingPrice;

    @Column(name = "selling_price")
    private BigDecimal sellingPrice;

    @Column(name = "min_selling_price")
    private BigDecimal minSellingPrice;

    @Column(name = "new_buying_price")
    private BigDecimal newBuyingPrice;

    @Column(name = "new_selling_price")
    private BigDecimal newSellingPrice;

    @Column(name = "new_min_selling_price")
    private BigDecimal newMinSellingPrice;

    @Column(name = "type")
    private String type;

    @Column(name = "description")
    private String description;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn=new Date();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public BigDecimal getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(BigDecimal buyingPrice) {
        this.buyingPrice = buyingPrice;
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

    public BigDecimal getNewBuyingPrice() {
        return newBuyingPrice;
    }

    public void setNewBuyingPrice(BigDecimal newBuyingPrice) {
        this.newBuyingPrice = newBuyingPrice;
    }

    public BigDecimal getNewSellingPrice() {
        return newSellingPrice;
    }

    public void setNewSellingPrice(BigDecimal newSellingPrice) {
        this.newSellingPrice = newSellingPrice;
    }

    public BigDecimal getNewMinSellingPrice() {
        return newMinSellingPrice;
    }

    public void setNewMinSellingPrice(BigDecimal newMinSellingPrice) {
        this.newMinSellingPrice = newMinSellingPrice;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
}
