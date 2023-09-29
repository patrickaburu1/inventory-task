package com.patrick.inventorymanagementtask.entities.transactions;

import com.patrick.inventorymanagementtask.utils.AppConstants;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author patrick on 7/25/20
 * @project inventory
 */
@Entity
@Table(name = "transaction_types")
public class TransactionTypes implements Serializable {
    public static String RENEW_PACKAGE="RENEW_PACKAGE";
    public static String MARKETER_WITHDRAWAL ="MARKETER_WITHDRAWAL";
    public static String MARKETER_EARNING="MARKETER_EARNING";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "flag")
    private String flag= AppConstants.ACTIVE_RECORD;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn=new Date();

    @Column(name = "updated_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }
}
