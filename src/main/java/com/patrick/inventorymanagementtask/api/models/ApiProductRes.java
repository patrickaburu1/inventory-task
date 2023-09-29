package com.patrick.inventorymanagementtask.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.patrick.inventorymanagementtask.utils.AppConstants;

import java.math.BigDecimal;
import java.util.Date;


public class ApiProductRes {
    private Long id;
    private String name;
    private String code;
    private String flag = AppConstants.ACTIVE_RECORD;
    private String description;
    private BigDecimal sellingPrice;
    private BigDecimal buyingPrice;
    private Integer categoryId;
    private Integer stock;
    private BigDecimal minSellingPrice;
    private Integer reOrderLevel = 10;
    @JsonIgnore
    private Date expiryDate = new Date();
    private Date createdOn = new Date();
    private Date updatedOn = new Date();
    private String categoryName;
    private Long shopId;
    public Long getId() {
        return id;
    }
    private LastSupplier lastSupplier;

    public static class LastSupplier{
        private Integer id;
        private String name;
        private String phone;
        private String email;
        private String address;
        private String description;
        private String mpesaPhone=null;
        private String bank=null;
        private String bankAccount=null;
        private Date createdOn;
        private Date updatedOn;
        private Long shopId;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getMpesaPhone() {
            return mpesaPhone;
        }

        public void setMpesaPhone(String mpesaPhone) {
            this.mpesaPhone = mpesaPhone;
        }

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        public String getBankAccount() {
            return bankAccount;
        }

        public void setBankAccount(String bankAccount) {
            this.bankAccount = bankAccount;
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

        public Long getShopId() {
            return shopId;
        }

        public void setShopId(Long shopId) {
            this.shopId = shopId;
        }
    }

    public LastSupplier getLastSupplier() {
        return lastSupplier;
    }

    public void setLastSupplier(LastSupplier lastSupplier) {
        this.lastSupplier = lastSupplier;
    }

    public ApiProductRes setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ApiProductRes setName(String name) {
        this.name = name;
        return this;
    }

    public String getCode() {
        return code;
    }

    public ApiProductRes setCode(String code) {
        this.code = code;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ApiProductRes setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public ApiProductRes setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
        return this;
    }

    public BigDecimal getBuyingPrice() {
        return buyingPrice;
    }

    public ApiProductRes setBuyingPrice(BigDecimal buyingPrice) {
        this.buyingPrice = buyingPrice;
        return this;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public ApiProductRes setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public ApiProductRes setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public ApiProductRes setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public Integer getStock() {
        return stock;
    }

    public ApiProductRes setStock(Integer stock) {
        this.stock = stock;
        return this;
    }

    public BigDecimal getMinSellingPrice() {
        return minSellingPrice;
    }

    public ApiProductRes setMinSellingPrice(BigDecimal minSellingPrice) {
        this.minSellingPrice = minSellingPrice;
        return this;
    }

    public Integer getReOrderLevel() {
        return reOrderLevel;
    }

    public ApiProductRes setReOrderLevel(Integer reOrderLevel) {
        this.reOrderLevel = reOrderLevel;
        return this;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public ApiProductRes setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }


    public String getFlag() {
        return flag;
    }

    public ApiProductRes setFlag(String flag) {
        this.flag = flag;
        return this;
    }

    public Long getShopId() {
        return shopId;
    }

    public ApiProductRes setShopId(Long shopId) {
        this.shopId = shopId;
        return this;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


}

