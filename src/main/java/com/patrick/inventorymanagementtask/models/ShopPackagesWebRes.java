package com.patrick.inventorymanagementtask.models;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author patrick on 6/12/20
 * @project myduka-pos
 */
public class ShopPackagesWebRes {

    private Long id;
    private String packageName;
    private String description;
    private List<ShopPlansWebRes> plans;
    private String colorCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ShopPlansWebRes> getPlans() {
        return plans;
    }

    public void setPlans(List<ShopPlansWebRes> plans) {
        this.plans = plans;
    }

    public static class ShopPlansWebRes{

        private Long planId;
        private Integer duration;
        private String plan;
        private BigDecimal amount;

        public Long getPlanId() {
            return planId;
        }

        public void setPlanId(Long planId) {
            this.planId = planId;
        }

        public Integer getDuration() {
            return duration;
        }

        public void setDuration(Integer duration) {
            this.duration = duration;
        }

        public String getPlan() {
            return plan;
        }

        public void setPlan(String plan) {
            this.plan = plan;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }
    }
}
