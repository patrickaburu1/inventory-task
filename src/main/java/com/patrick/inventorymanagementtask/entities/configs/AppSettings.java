package com.patrick.inventorymanagementtask.entities.configs;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author patrick on 6/12/20
 * @project inventory
 */
@Entity
@Table(name = "app_settings")
public class AppSettings implements Serializable {

    @Id
    @Column( name="id")
    private Long id;

    @Column( name="name")
    private String name;

    @Column( name="code")
    private String code;

    @Column( name="value")
    private String value;

    @Column( name="flag")
    private String flag;

    @Column( name="description")
    private String description;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
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
