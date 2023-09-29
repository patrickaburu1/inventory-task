package com.patrick.inventorymanagementtask.models.products;

/**
 * @author patrick on 4/16/20
 * @project myduka-pos
 */
public class AddInvoiceInfoReq {

    private String invoiceNo;
    private Long supplier;
    private String status,date;

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Long getSupplier() {
        return supplier;
    }

    public void setSupplier(Long supplier) {
        this.supplier = supplier;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
