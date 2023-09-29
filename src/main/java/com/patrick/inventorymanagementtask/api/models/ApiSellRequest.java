package com.patrick.inventorymanagementtask.api.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author patrick on 4/25/20
 * @project inventory
 */
public class ApiSellRequest {
    @NotNull
    private Long shopId;
    @NotNull
    private BigDecimal totalAmount;
    @NotNull
    private BigDecimal cashGiven;
    @NotNull
    private BigDecimal change;

    private String referenceNo,customerPhone;
    @NotNull
    private List<SoldItems> soldItems;
    @NotNull
    private List<SellPaymentMethod> paymentMethods;

    public static  class SellPaymentMethod{
        @NotNull
        private String paymentMethod;
        @NotNull
        private BigDecimal amount;
        private String referenceNo;

        public String getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public String getReferenceNo() {
            return referenceNo;
        }

        public void setReferenceNo(String referenceNo) {
            this.referenceNo = referenceNo;
        }
    }
    public static class SoldItems{
        @NotNull
        private Long productId;
        @NotNull
        @Min(value = 1)
        private Integer quantity;
        @NotNull
        @Min(value = 1)
        private BigDecimal sellingPrice;

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

        public BigDecimal getSellingPrice() {
            return sellingPrice;
        }

        public void setSellingPrice(BigDecimal sellingPrice) {
            this.sellingPrice = sellingPrice;
        }
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getCashGiven() {
        return cashGiven;
    }

    public void setCashGiven(BigDecimal cashGiven) {
        this.cashGiven = cashGiven;
    }

    public BigDecimal getChange() {
        return change;
    }

    public void setChange(BigDecimal change) {
        this.change = change;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public List<SoldItems> getSoldItems() {
        return soldItems;
    }

    public void setSoldItems(List<SoldItems> soldItems) {
        this.soldItems = soldItems;
    }

    public List<SellPaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<SellPaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }
}
