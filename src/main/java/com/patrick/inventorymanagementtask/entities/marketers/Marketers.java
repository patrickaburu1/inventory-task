package com.patrick.inventorymanagementtask.entities.marketers;

import com.patrick.inventorymanagementtask.entities.user.Users;
import com.patrick.inventorymanagementtask.utils.AppConstants;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author patrick on 7/25/20
 * @project inventory
 */
@Entity
@Table(name = "marketers")
public class Marketers implements Serializable {

    public static String MARKETER_STATUS_PENDING="PENDING";
    public static String MARKETER_STATUS_APPROVED="APPROVED";
    public static String MARKETER_STATUS_SUSPENDED="SUSPENDED";

    public static String MARKETER_TYPE_LEADER="LEADER";
    public static String MARKETER_TYPE_NORMAL="NORMAL";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "flag")
    private String flag= AppConstants.ACTIVE_RECORD;

    @Column(name = "status")
    private String status=MARKETER_STATUS_PENDING;

    @Column(name = "referral_code")
    private String referralCode;

    @Column(name = "percent_rate")
    private BigDecimal percentRate;

    @Column(name = "wallet_id")
    private Long walletId;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "one_off_earning")
    private Boolean onOffEarning=false;

    @Column(name = "firebase_token")
    private String firabaseToken;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "app_version")
    private String appVersion;

    @Column(name = "last_login")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogin;

    @Column(name = "marketer_type")
    private String marketerType=MARKETER_TYPE_NORMAL;

    @Column(name = "lead_by_marketer_id")
    private Long leadByMarketerId;

    @Column(name = "earn_from_my_team")
    private Boolean earnFromMyTeam;

    @Column(name = "leader_percent")
    private BigDecimal leaderPercent=BigDecimal.ZERO;

    @Column(name = "ip")
    private String ip;

    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn=new Date();

    @Column(name = "updated_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;

    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false,updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Users userLink;


    public Users getUserLink() {
        return userLink;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public BigDecimal getPercentRate() {
        return percentRate;
    }

    public void setPercentRate(BigDecimal percentRate) {
        this.percentRate = percentRate;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
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

    public Long getWalletId() {
        return walletId;
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }

    public Boolean getOnOffEarning() {
        return onOffEarning;
    }

    public void setOnOffEarning(Boolean onOffEarning) {
        this.onOffEarning = onOffEarning;
    }

    public String getFirabaseToken() {
        return firabaseToken;
    }

    public void setFirabaseToken(String firabaseToken) {
        this.firabaseToken = firabaseToken;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getMarketerType() {
        return marketerType;
    }

    public void setMarketerType(String marketerType) {
        this.marketerType = marketerType;
    }

    public Long getLeadByMarketerId() {
        return leadByMarketerId;
    }

    public void setLeadByMarketerId(Long leadByMarketerId) {
        this.leadByMarketerId = leadByMarketerId;
    }

    public Boolean getEarnFromMyTeam() {
        return earnFromMyTeam;
    }

    public void setEarnFromMyTeam(Boolean earnFromMyTeam) {
        this.earnFromMyTeam = earnFromMyTeam;
    }

    public BigDecimal getLeaderPercent() {
        return leaderPercent;
    }

    public void setLeaderPercent(BigDecimal leaderPercent) {
        this.leaderPercent = leaderPercent;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
