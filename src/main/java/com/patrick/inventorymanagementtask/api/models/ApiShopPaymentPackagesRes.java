package com.patrick.inventorymanagementtask.api.models;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author patrick on 6/5/20
 * @project inventory
 */
public class ApiShopPaymentPackagesRes {
    private String name;
    private String description;

    private Boolean trial=false;

    public Boolean getTrial() {
        return trial;
    }

    public void setTrial(Boolean trial) {
        this.trial = trial;
    }

    private List<ApiShopPlansRes> plans;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ApiShopPlansRes> getPlans() {
        return plans;
    }

    public void setPlans(List<ApiShopPlansRes> plans) {
        this.plans = plans;
    }

    public static class ApiShopPlansRes{
        private Long id;
        private String plan;
        private Integer duration;
        private BigDecimal amount;
        private Boolean currentPlan=false;

        public Boolean getCurrentPlan() {
            return currentPlan;
        }

        public void setCurrentPlan(Boolean currentPlan) {
            this.currentPlan = currentPlan;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getPlan() {
            return plan;
        }

        public void setPlan(String plan) {
            this.plan = plan;
        }

        public Integer getDuration() {
            return duration;
        }

        public void setDuration(Integer duration) {
            this.duration = duration;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }
    }
}
