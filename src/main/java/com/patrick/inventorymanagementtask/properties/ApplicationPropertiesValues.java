package com.patrick.inventorymanagementtask.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationPropertiesValues {

    @Value("${jwt.expires_in}")
    public long expiresIn;

    @Value("${jwt.header}")
    public String authHeader;

    @Value("${api.access_key}")
    public String apiAccessKey;

    @Value("${app.name}")
    public String appName;

    @Value("${jwt.secret}")
    public String base64SecretBytes;

    @Value("${jwt.token_refresh_time_before_expiry}")
    public int tokenRefreshTimeBeforeExpiry;

    @Value("${base.url.http}")
    public String httpBaseUrl;

    @Value("${play.store.link}")
    public String playStoreLink;

    @Value("${payment.gateway.url}")
    public String PAYMENT_GATEWAY;

    @Value("${mpesa.shortcode}")
    public String MPESA_SHORT_CODE;

    @Value("${mpesa.b2c.shortcode}")
    public String MPESA_B2C_SHORT_CODE;

    @Value("${mpesa.username}")
    public String MPESA_USERNAME;

    @Value("${mpesa.password}")
    public String MPESA_PASS_KEY;

    @Value("${spring.sendgrid.api-key}")
    public String SEND_GRID_API_KEY;

    @Value("${infolink.sms.url}")
    public String INFO_LINK_SMS_URL;
}
