package com.patrick.inventorymanagementtask.security.api;


import com.patrick.inventorymanagementtask.entities.user.Users;
import com.patrick.inventorymanagementtask.repositories.user.UserRepository;
import com.patrick.inventorymanagementtask.security.TimeProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

//import com.ganjipayments.api.security.configs.SecretKeyProvider;

/**
 * @author SamKyalo
 */

@Component
public class TokenHelper {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${app.name}")
    private String APP_NAME;

    @Value("${jwt.secret}")
    public String base64SecretBytes;

    @Value("${jwt.expires_in}")
    private int EXPIRES_IN;

    @Value("${jwt.token_refresh_time_before_expiry}")
    private int TOKEN_REFRESH_TIME_BEFORE_EXPIRY;


    @Value("${jwt.header}")
    private String AUTH_HEADER;

    @Value("${api.access_key}")
    public String API_ACCESS_KEY;

    static final String AUDIENCE_UNKNOWN = "unknown";
    static final String AUDIENCE_WEB = "web";
    static final String AUDIENCE_MOBILE = "mobile";
    static final String AUDIENCE_TABLET = "tablet";

    private static final Key secret = MacProvider.generateKey(SignatureAlgorithm.HS512);
    private static final byte[] secretBytes = secret.getEncoded();
//    private static final String base64SecretBytes = Base64.getEncoder().encodeToString(secretBytes);

    @Autowired
    TimeProvider timeProvider;

    @Autowired
    private UserRepository userRepository;

    private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    public String getPhoneNumberFromToken(String token) {

        if(token != null){
            if(token.startsWith("Bearer ")){
                token = token.substring(7);
            }
        }

        String phoneNo;
        try {
            final Claims claims = getAllClaimsFromToken(token);
            phoneNo = claims.getSubject();

         /*   logger.info("*****TOKEN AUDIENCE: " + claims.getAudience());
            logger.info("*****TOKEN SUBJECT: " + claims.getSubject());
            logger.info("*****TOKEN CREATION: " + claims.getIssuedAt());
            logger.info("*****TOKEN EXPIRATION: " + claims.getExpiration());
            logger.info("*****TOKEN ISSUER: " + claims.getIssuer());*/

        } catch (Exception e) {
            phoneNo = null;
        }
        return phoneNo;
    }

    public Date getIssuedAtDateFromToken(String token) {

        if(token != null){
            if(token.startsWith("Bearer ")){
                token = token.substring(7);
            }
        }

        Date issueAt;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            issueAt = claims.getIssuedAt();
        } catch (Exception e) {
            issueAt = null;
        }
        return issueAt;
    }

    public String getAudienceFromToken(String token) {
        String audience;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            audience = claims.getAudience();
        } catch (Exception e) {
            audience = null;
        }
        return audience;
    }

    public String getAPIAccessKey() {
        return this.API_ACCESS_KEY;
    }

    public String refreshToken(String token) {

        if(token != null){
            if(token.startsWith("Bearer ")){
                token = token.substring(7);
            }
        }

        String refreshedToken;
        Date a = timeProvider.now();
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            claims.setIssuedAt(a);
            refreshedToken = Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(generateExpirationDate())
                    .signWith( SIGNATURE_ALGORITHM, base64SecretBytes )
                    .compact();
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public String generateToken(String phoneNo) {
        String audience = generateAudience();
        try {
            return Jwts.builder()
                    .setIssuer( APP_NAME )
                    .setSubject(phoneNo)
                    .setAudience(audience)
                    .setIssuedAt(timeProvider.now())
                    .setExpiration(generateExpirationDate())
                    .signWith( SIGNATURE_ALGORITHM, base64SecretBytes )
                    .compact();

        } catch (Exception e) {
            logger.error("An error has occurred: ",e);
        }

        return null;
    }

    public String generateMartekingUserToken(String phoneNo) {
        String audience = generateAudience();
        try {
            return Jwts.builder()
                    .setIssuer( APP_NAME )
                    .setSubject(phoneNo)
                    .setAudience(audience)
                    .setIssuedAt(timeProvider.now())
                    .setExpiration(generateExpirationDateMarketing())
                    .signWith( SIGNATURE_ALGORITHM, base64SecretBytes )
                    .compact();

        } catch (Exception e) {
            logger.error("An error has occurred: ",e);
        }

        return null;
    }

    private String generateAudience() {
        String audience = AUDIENCE_UNKNOWN;
        audience = AUDIENCE_MOBILE;
        return audience;
    }


    public Date getExpirationDateFromToken(String token) {

        if(token != null){
            if(token.startsWith("Bearer ")){
                token = token.substring(7);
            }
        }

        Date expiration;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }


    //Refresh Token 30 Seconds before Expiry
    public boolean shouldTokenBeRefreshed(String token){
        if(token != null){
            if(token.startsWith("Bearer ")){
                token = token.substring(7);
            }

            if(getIssuedAtDateFromToken(token) != null ){
                if(!isTokenExpired(token)){
                    long secondsInMilli = 1000;
                    //long issuedTimeSeconds = getIssuedAtDateFromToken(token).getTime()/ secondsInMilli;
                    long currentTimeSeconds = generateCurrentDate().getTime()/ secondsInMilli;
                    long expirationTimeSeconds = getExpirationDateFromToken(token).getTime()/ secondsInMilli;

                    long secondsBeforeExpiry = expirationTimeSeconds - currentTimeSeconds;

                    return secondsBeforeExpiry > 0 && secondsBeforeExpiry <= TOKEN_REFRESH_TIME_BEFORE_EXPIRY ;
                }
            }
        }

        return false;
    }

    private Date generateCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    public boolean isTokenExpired(String token) {
        boolean tokenExpired = true;

        if(token != null){
            long secondsInMilli = 1000 * 60;

            if(getIssuedAtDateFromToken(token) != null ){
                long expiryTimeSeconds = getExpirationDateFromToken(token).getTime()/ secondsInMilli;

                //long issuedTimeSeconds = getIssuedAtDateFromToken(token).getTime()/ secondsInMilli;

                //long expiryTimeSeconds = issuedTimeSeconds + Long.valueOf(expiresIn);

                Date date = new Date(System.currentTimeMillis());
                logger.info("Token Expiry Time: "+getExpirationDateFromToken(token).toString()
                        +"\nCurrent Time: "+getExpirationDateFromToken(token).toString());

                long currentTimeSeconds =   date.getTime()/secondsInMilli;

                if(currentTimeSeconds < expiryTimeSeconds)
                    tokenExpired = false;
            }
        }

        return tokenExpired;
    }

    public boolean isTokenValid(String token){
        try {
            Claims claims;
            claims = Jwts.parser()
                    .setSigningKey(base64SecretBytes)
                    .parseClaimsJws(token)
                    .getBody();

            if(claims == null){
                return false;
            }
            else {
                return true;
            }
        } catch (Exception e) {
            logger.error("An error has occurred: ",e);
            return false;
        }
    }

    private Claims getAllClaimsFromToken(String token) {
        Claims claims;

        try {
            claims = Jwts.parser()
                    // .setSigningKey(keyProvider.getKey())
                    .setSigningKey(base64SecretBytes)
                    .parseClaimsJws(token)
                    .getBody();

        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Date generateExpirationDate() {
        long expiresIn =  EXPIRES_IN;
        return new Date(timeProvider.now().getTime() + expiresIn * 1000);
    }

    private Date generateExpirationDateMarketing() {
        long expiresIn =  300L;
        return new Date(timeProvider.now().getTime() + expiresIn * 1000);
    }


    public int getExpiredIn() {
        return EXPIRES_IN;
    }

    public Map<String, Object> validateToken(String token, String userPhoneNumber) {

        Map<String, Object> response = new HashMap<>();
        if(token != null){
            if(token.startsWith("Bearer ")){
                token = token.substring(7);
            }
        }

        // Users user = (Users) userDetails;

        Optional<Users> optional = userRepository.findByPhoneAndActive(userPhoneNumber,1);
        if(!optional.isPresent()) {
            response.put("valid", false);
            response.put("user", null);
            return response;
        }

        Users user = optional.get();
        final String phoneNo = getPhoneNumberFromToken(token);

        logger.info("*******PHONE_NUMBER_TOKEN2: " + phoneNo);

        final Date created = getIssuedAtDateFromToken(token);
        response.put ("valid", phoneNo != null && phoneNo.equals(user.getPhone()));
        response.put("user", user);
        return response;
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    public String getToken(String authHeader) {
        /**
         *  Getting the token from Authentication header
         *  e.g Bearer your_token
         */
        // String authHeader = getAuthHeaderFromHeader( request );
        //String authHeader = getAuthHeaderFromHeader();
        if ( authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }



}
