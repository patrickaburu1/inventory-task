package com.patrick.inventorymanagementtask.models;

/**
 * @author patrick on 4/28/20
 * @project myduka-pos
 */
public class EditInvoiceReq {
    private Long invoiceId;
    private String status;

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
