package com.patrick.inventorymanagementtask.api.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author patrick on 4/20/20
 * @project inventory
 */
public class ApiAddStockViaInvoiceReq {
    @NotNull
    private Long shopId;
    @NotNull
    private List<ApiAddStockViaInvoiceReqStock> stock;

    public static class ApiAddStockViaInvoiceReqStock{

        @NotNull
        private Long productId;
        @NotNull
        @Min(value = 1)
        private Integer quantity;
        @NotNull
        private BigDecimal buyingPrice;
        @NotNull
        private BigDecimal minSellingPrice;
        @NotNull
        private BigDecimal sellingPrice;
        @NotNull
        private ApiAddStockViaInvoiceReqStockSupplier supplier;
        private ApiAddStockViaInvoiceReqInvoice invoice;

        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public BigDecimal getBuyingPrice() {
            return buyingPrice;
        }

        public void setBuyingPrice(BigDecimal buyingPrice) {
            this.buyingPrice = buyingPrice;
        }

        public BigDecimal getMinSellingPrice() {
            return minSellingPrice;
        }

        public void setMinSellingPrice(BigDecimal minSellingPrice) {
            this.minSellingPrice = minSellingPrice;
        }

        public BigDecimal getSellingPrice() {
            return sellingPrice;
        }

        public void setSellingPrice(BigDecimal sellingPrice) {
            this.sellingPrice = sellingPrice;
        }

        public ApiAddStockViaInvoiceReqStockSupplier getSupplier() {
            return supplier;
        }

        public void setSupplier(ApiAddStockViaInvoiceReqStockSupplier supplier) {
            this.supplier = supplier;
        }

        public ApiAddStockViaInvoiceReqInvoice getInvoice() {
            return invoice;
        }

        public void setInvoice(ApiAddStockViaInvoiceReqInvoice invoice) {
            this.invoice = invoice;
        }

        public static class ApiAddStockViaInvoiceReqStockSupplier{
            @NotNull
            private Integer supplierId;
            private String name;

            public Integer getSupplierId() {
                return supplierId;
            }

            public void setSupplierId(Integer supplierId) {
                this.supplierId = supplierId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class ApiAddStockViaInvoiceReqInvoice{
            private String invoiceNo;
            private Boolean status;
            private String date;

            public String getInvoiceNo() {
                return invoiceNo;
            }

            public void setInvoiceNo(String invoiceNo) {
                this.invoiceNo = invoiceNo;
            }

            public Boolean getStatus() {
                return status;
            }

            public void setStatus(Boolean status) {
                this.status = status;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }
        }

    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public List<ApiAddStockViaInvoiceReqStock> getStock() {
        return stock;
    }

    public void setStock(List<ApiAddStockViaInvoiceReqStock> stock) {
        this.stock = stock;
    }
}
