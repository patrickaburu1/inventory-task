package com.patrick.inventorymanagementtask.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author patrick on 11/28/19
 * @project shop-pos
 */
@Entity
@Table(name = "user_verify")
public class UserVerify implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "username")
    private String userName;

    @Column(name = "active")
    private Integer active=1;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn=new Date();

    @Column(name = "updated_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn=new Date();

    public Long getId() {
        return id;
    }

    public UserVerify setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return code;
    }

    public UserVerify setCode(String code) {
        this.code = code;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public UserVerify setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public Integer getActive() {
        return active;
    }

    public UserVerify setActive(Integer active) {
        this.active = active;
        return this;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public UserVerify setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public UserVerify setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }
}
