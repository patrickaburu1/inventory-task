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
@Table(name = "transactions")
public class Transactions extends TransactionBaseEntity implements Serializable {

    @Column(name = "reference_no")
    private String referenceNo;

    @Column(name = "transaction_type_no")
    private Long transactionTypeNo;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "narration")
    private String narration;

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public Long getTransactionTypeNo() {
        return transactionTypeNo;
    }

    public void setTransactionTypeNo(Long transactionTypeNo) {
        this.transactionTypeNo = transactionTypeNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }
}
