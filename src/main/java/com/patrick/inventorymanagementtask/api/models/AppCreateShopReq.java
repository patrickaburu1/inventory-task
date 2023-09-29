package com.patrick.inventorymanagementtask.api.models;

import org.springframework.lang.NonNull;

import javax.validation.constraints.NotNull;

/**
 * @author patrick on 4/8/20
 * @project  inventory
 */
public class AppCreateShopReq {
    @NotNull
    private String name;
    private String description;
    private String  locationName;
    private Location location;
    @NotNull
    private Long categoryId;
    private String referralCode;

    public static class Location{
        public Location(Long latitude, Long longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        private Long latitude=0L;
        private Long longitude=0L;
        private String address;
        public Long getLatitude() {
            return latitude;
        }

        public void setLatitude(Long latitude) {
            this.latitude = latitude;
        }

        public Long getLongitude() {
            return longitude;
        }

        public void setLongitude(Long longitude) {
            this.longitude = longitude;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        @Override
        public String toString() {
            return "{" +longitude  + ","+ latitude +","+address+
                    '}';
        }
    }
    public String getName() {
        return name;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @NonNull
    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(@NonNull Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }
}
