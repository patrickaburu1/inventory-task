package com.patrick.inventorymanagementtask.entities.transactions;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author patrick on 7/25/20
 * @project inventory
 */
@Entity
@Table(name = "mpesa_transactions")
public class MpesaTransactions extends TransactionBaseEntity implements Serializable {

    @Column(name = "reference_no")
    private String referenceNo;

    @Column(name = "transaction_id")
    private long transactionId;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "mpesa_ref")
    private String mpesaRef;

    @Column(name = "narration")
    private String narration;

    @Column(name = "checkout_id")
    private String checkoutId;

    @Column(name = "names")
    private String names;


    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMpesaRef() {
        return mpesaRef;
    }

    public void setMpesaRef(String mpesaRef) {
        this.mpesaRef = mpesaRef;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getCheckoutId() {
        return checkoutId;
    }

    public void setCheckoutId(String checkoutId) {
        this.checkoutId = checkoutId;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }
}
