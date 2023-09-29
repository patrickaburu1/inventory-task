package com.patrick.inventorymanagementtask.security.api;


import com.patrick.inventorymanagementtask.utils.ResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author SamKyalo
 */
@Service
public class TokenService {


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TokenHelper tokenHelper;

    @Value("${api.access_key}")
    private String accessKey;
    public Map<String, Object> mapResponse(Map<String, Object> response, Map<String, Object> tokenRefreshResponse  ) {

        if(tokenRefreshResponse.get("status").equals("00")){
            response.put("accessToken",tokenRefreshResponse.get("accessToken"));
        }
        else if(tokenRefreshResponse.get("status").equals("003")){
            response.clear();
            response.put("status", "003");
            response.put("message", "Session Expired!");
        }

        return response;
    }

    /**
     * Refresh Token
     *
     * @param
     * @return Map
     */

    public ResponseModel refreshAccessToken(String authToken){

       // Map<String, Object> response = new HashMap<>();
       ResponseModel response=new ResponseModel();

        String refreshedToken="";

        if(authToken.startsWith("Bearer "))
            authToken = authToken.substring(7);

        if (authToken != null && accessKey != null) {

            if(tokenHelper.isTokenExpired(authToken)){

                return new ResponseModel("003","Session Expired!");
            }
             else{

                if(tokenHelper.shouldTokenBeRefreshed(authToken)){
                    refreshedToken = tokenHelper.refreshToken(authToken);
                  /*  response.put("refreshToken",refreshedToken);
                    response.put("status", "00");
                    response.put("message", "Successful");*/
                    logger.info("************ token refreshed "+refreshedToken);
                    response.setStatus("00");
                    response.setMessage("Successful refreshed token.");
                    response.setRefreshedToken(refreshedToken);
                    return response;
                }
                else{

                    logger.info("************ token not refreshed");
                    //Token not Refreshed
                /*    response.put("status", "01");
                    response.put("message", "Not Refreshed");*/

                    return new ResponseModel("01","Not Refreshed!");
                }

            }

        }

        return response;

    }

}
