package com.patrick.inventorymanagementtask.entities.products;

import com.patrick.inventorymanagementtask.entities.user.Users;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author patrick on 8/17/19
 * @project  inventory
 */
@Entity
@Table(name = "cleared_debts")
public class ClearedDebts  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "check_out_id")
    private Long checkOutId;

    @Column(name = "created_by")
    private Integer createdBy;


    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @JoinColumn(name = "check_out_id", referencedColumnName = "id", insertable = false,updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private CheckOut checkOutLink;

    @JoinColumn(name = "created_by", referencedColumnName = "id", insertable = false,updatable = false)
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

    public ClearedDebts setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getCheckOutId() {
        return checkOutId;
    }

    public ClearedDebts setCheckOutId(Long checkOutId) {
        this.checkOutId = checkOutId;
        return this;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public ClearedDebts setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public ClearedDebts setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public ClearedDebts setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public CheckOut getCheckOutLink() {
        return checkOutLink;
    }

    public Users getUsersLink() {
        return usersLink;
    }

    public Long getShopId() {
        return shopId;
    }

    public ClearedDebts setShopId(Long shopId) {
        this.shopId = shopId;
        return this;
    }

    public Shop getShopLink() {
        return shopLink;
    }
}
