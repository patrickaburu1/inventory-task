package com.patrick.inventorymanagementtask.entities.products;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author patrick on 8/9/19
 * @project shop-pos
 */
@Entity
@Table(name = "payment_methods")
public class PaymentMethods implements Serializable {

    public static String MPESA="MPESA";
    public static String CASH="CASH";
    public static String DEBT="DEBT";
    public static String CREDIT="CREDIT";

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


    @Column(name = "description")
    private String description;


    @Column(name = "is_active")
    private Boolean isActive=true;

    @Transient
    private Boolean checked=false;

    @Transient
    private BigDecimal amount;

    @Transient
    private String paymentReferenceNo;

    public String getPaymentReferenceNo() {
        return paymentReferenceNo;
    }

    public void setPaymentReferenceNo(String paymentReferenceNo) {
        this.paymentReferenceNo = paymentReferenceNo;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Long getId() {
        return id;
    }

    public PaymentMethods setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public PaymentMethods setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public PaymentMethods setDescription(String description) {
        this.description = description;
        return this;
    }

    public Boolean getActive() {
        return isActive;
    }

    public PaymentMethods setActive(Boolean active) {
        isActive = active;
        return this;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
