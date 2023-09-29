package com.patrick.inventorymanagementtask.entities.products;

import com.patrick.inventorymanagementtask.entities.user.Users;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author patrick on 8/17/19
 * @project shop-pos
 */
@Entity
@Table(name = "debits")
public class Debits implements Serializable {
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

    @Column(name = "debit")
    private Boolean debit=false;


    @Column(name = "expense")
    private Boolean expense=false;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn=new Date();

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

    public Debits setId(Long id) {
        this.id = id;
        return this;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public Debits setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public Debits setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Debits setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Debits setDescription(String description) {
        this.description = description;
        return this;
    }

    public Boolean getDebit() {
        return debit;
    }

    public Debits setDebit(Boolean debit) {
        this.debit = debit;
        return this;
    }

    public Boolean getExpense() {
        return expense;
    }

    public Debits setExpense(Boolean expense) {
        this.expense = expense;
        return this;
    }

    public Users getUsersLink() {
        return usersLink;
    }

    public Debits setUsersLink(Users usersLink) {
        this.usersLink = usersLink;
        return this;
    }

    public Long getShopId() {
        return shopId;
    }

    public Debits setShopId(Long shopId) {
        this.shopId = shopId;
        return this;
    }


}
