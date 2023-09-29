package com.patrick.inventorymanagementtask.entities.user;

import com.patrick.inventorymanagementtask.entities.products.Shop;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author patrick on 9/3/19
 * @project shop-pos
 */
@Entity
@Table(name = "audit_log")
public class AuditLog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "flag")
    private String flag;

    @Column(name = "origin")
    private String origin;

    @Column(name = "ip")
    private String ip;

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

    public Long getId() {
        return id;
    }

    public AuditLog setId(Long id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public AuditLog setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public AuditLog setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public String getFlag() {
        return flag;
    }

    public AuditLog setFlag(String flag) {
        this.flag = flag;
        return this;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public AuditLog setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public AuditLog setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public Long getShopId() {
        return shopId;
    }

    public AuditLog setShopId(Long shopId) {
        this.shopId = shopId;
        return this;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
