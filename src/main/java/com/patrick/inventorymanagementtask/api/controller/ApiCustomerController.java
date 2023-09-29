package com.patrick.inventorymanagementtask.api.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.patrick.inventorymanagementtask.api.models.ApiEditCustomerReq;
import com.patrick.inventorymanagementtask.api.services.ApiShopService;
import com.patrick.inventorymanagementtask.api.utils.ApiAbstractController;
import com.patrick.inventorymanagementtask.security.api.TokenService;
import com.patrick.inventorymanagementtask.utils.AppConstants;
import com.patrick.inventorymanagementtask.utils.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

/**
 * @author patrick on 5/18/20
 * @project inventory
 */
@RestController
@RequestMapping(value = "/api/v1/customer")
public class ApiCustomerController extends ApiAbstractController {
    @Autowired
    private ApiShopService apiShopService;
    @Autowired
    private TokenService tokenService;

    @GetMapping("/all")
    public ResponseModel customersList(@RequestHeader("Authorization") String authHeader, @RequestHeader(value = "Access-Key", required = false) String accessKey,
                                       @RequestParam("shopId") @NotNull Long shopId,
                                       @RequestParam("page") @NotNull Integer page,
                                       @RequestParam("size") @NotNull Integer size, HttpServletRequest httpServletRequest ) {
        ResponseModel response;
        try {
            response = apiShopService.customers(shopId,page,size, httpServletRequest);
            response.setRefreshedToken(refreshedToken(authHeader));
        } catch (Exception e) {
            return apiError("/all", getObjectMapper().convertValue(null, JsonNode.class), e);
        }
        return apiLogger("/all", getObjectMapper().convertValue(null, JsonNode.class), response);

    }

    @PostMapping( "/edit")
    public ResponseModel editCustomer(@RequestHeader("Authorization") String authHeader, @RequestBody ApiEditCustomerReq request, HttpServletRequest httpServletRequest ) {
        ResponseModel response;
        try {
            response =apiShopService.editCustomer(request, httpServletRequest);
            response.setRefreshedToken(refreshedToken(authHeader));
        } catch (Exception e) {
            return apiError("/edit", getObjectMapper().convertValue(request, JsonNode.class), e);
        }
        return apiLogger("/edit", getObjectMapper().convertValue(request, JsonNode.class), response);
    }

    @PostMapping( "/create")
    public ResponseModel createCustomer(@RequestHeader("Authorization") String authHeader, @RequestBody ApiEditCustomerReq request, HttpServletRequest httpServletRequest ) {
        ResponseModel response;
        try {
            response =apiShopService.editCustomer(request, httpServletRequest);
            response.setRefreshedToken(refreshedToken(authHeader));
        } catch (Exception e) {
            return apiError("/create", getObjectMapper().convertValue(request, JsonNode.class), e);
        }
        return apiLogger("/create", getObjectMapper().convertValue(request, JsonNode.class), response);
    }

    @GetMapping("/search")
    public ResponseModel searchCustomer(@RequestHeader("Authorization") String authHeader, @RequestHeader(value = "Access-Key", required = false) String accessKey,
                                       @RequestParam("shopId") @NotNull Long shopId,
                                       @RequestParam("phoneNumber") @NotNull String phoneNumber, HttpServletRequest httpServletRequest ) {
        ResponseModel response;
        try {
            response = apiShopService.findCustomer(shopId,phoneNumber, httpServletRequest);
            response.setRefreshedToken(refreshedToken(authHeader));
        } catch (Exception e) {
            return apiError("/search", getObjectMapper().convertValue(null, JsonNode.class), e);
        }
        return apiLogger("/search", getObjectMapper().convertValue(null, JsonNode.class), response);

    }

    private String refreshedToken(String authHeader) {
        ResponseModel response = tokenService.refreshAccessToken(authHeader);
        if (response.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return response.getRefreshedToken();
        else
            return null;
    }

    /**
     * Called to render the response as generated by the class item
     *
     * @param url
     * @param request
     * @param response
     * @return ResponseModel
     */
    protected ResponseModel apiLogger(String url, JsonNode request, ResponseModel response) {
        return super.apiLogger("/api/v1/customer" + url, filterRequest(request, ""), response);
    }

    /**
     * Called to render the response as generated by the class item
     *
     * @param url
     * @param request
     * @param ex
     * @return ResponseModel
     */
    protected ResponseModel apiError(String url, JsonNode request, Exception ex) {
        return super.apiError("/api/v1/customer" + url, filterRequest(request, ""), ex);
    }
}