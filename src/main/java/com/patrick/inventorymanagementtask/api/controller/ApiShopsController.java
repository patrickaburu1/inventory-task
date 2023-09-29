package com.patrick.inventorymanagementtask.api.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.patrick.inventorymanagementtask.api.models.*;
import com.patrick.inventorymanagementtask.api.services.ApiShopService;
import com.patrick.inventorymanagementtask.api.utils.ApiAbstractController;
import com.patrick.inventorymanagementtask.security.api.TokenService;
import com.patrick.inventorymanagementtask.utils.AppConstants;
import com.patrick.inventorymanagementtask.utils.ResponseModel;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author patrick on 2/11/20
 * @project shop-pos
 */
@RestController
@RequestMapping(value = "/api/v1/shops")
@Api(value = "Duka Lite", description = "Operations pertaining to shop")
public class ApiShopsController extends ApiAbstractController {
    @Autowired
    private ApiShopService apiShopService;
    @Autowired
    private TokenService tokenService;

    @GetMapping(value = "")
    public ResponseModel shopsList(@RequestHeader("Authorization") String authHeader,
                                   @RequestHeader(value = "Access-Key", required = false) String accessKey) {
        ResponseModel response;
        try {
            response = apiShopService.getShops();
            response.setRefreshedToken(refreshedToken(authHeader));
        } catch (Exception e) {
            return apiError("", getObjectMapper().convertValue(null, JsonNode.class), e);
        }
        return apiLogger("", getObjectMapper().convertValue(null, JsonNode.class), response);

    }

    @GetMapping(value = "/packages")
    public ResponseModel packageList(@RequestHeader("Authorization") String authHeader,
                                     @RequestParam(value = "shopId", required = false) Long shopId) {
        ResponseModel response;
        try {
            response =apiShopService.shopsPackages(shopId);
            response.setRefreshedToken(refreshedToken(authHeader));
        } catch (Exception e) {
            return apiError("/packages", getObjectMapper().convertValue(null, JsonNode.class), e);
        }
        return apiLogger("/packages", getObjectMapper().convertValue(null, JsonNode.class), response);
    }

    /**88
     *
     * Create shop/ business
     *
     * */
    @PostMapping(value = "/create-shop")
    public ResponseModel addShop(@RequestHeader("Authorization") String authHeader,
                                 @RequestHeader(value = "Access-Key", required = false) String accessKey, @Valid  @RequestBody AppCreateShopReq request) {

        ResponseModel response;
        try {
            response =apiShopService.addShop(request);
            response.setRefreshedToken(refreshedToken(authHeader));
        } catch (Exception e) {
            return apiError("/create-shop", getObjectMapper().convertValue(request, JsonNode.class), e);
        }
        return apiLogger("/create-shop", getObjectMapper().convertValue(request, JsonNode.class), response);
    }

    /**
     *
     *
     * Update shop/ business
     *
     *
     * */
    @PostMapping(value = "/update-shop")
    public ResponseModel updateShop(@RequestHeader("Authorization") String authHeader,
                                 @RequestHeader(value = "Access-Key", required = false) String accessKey, @Valid  @RequestBody ApiEditShopReq request) {

        ResponseModel response;
        try {
            response =apiShopService.updateShopDetails(request);
            response.setRefreshedToken(refreshedToken(authHeader));
        } catch (Exception e) {
            return apiError("/update-shop", getObjectMapper().convertValue(request, JsonNode.class), e);
        }
        return apiLogger("/update-shop", getObjectMapper().convertValue(request, JsonNode.class), response);
    }


    @PostMapping("/set-primary")
    public ResponseModel setPrimaryShop(@RequestHeader("Authorization") String authHeader, @RequestHeader(value = "Access-Key", required = false) String accessKey,
                                       @RequestParam("shopId") @NotNull Long shopId, HttpServletRequest httpServletRequest ) {
        ResponseModel response;
        try {
            response = apiShopService.setPrimaryShop(shopId, httpServletRequest);
            response.setRefreshedToken(refreshedToken(authHeader));
        } catch (Exception e) {
            return apiError("/set-primary", getObjectMapper().convertValue(null, JsonNode.class), e);
        }
        return apiLogger("/set-primary", getObjectMapper().convertValue(null, JsonNode.class), response);

    }

    @PostMapping(value = "/subscribe")
    public ResponseModel subscribe(@RequestHeader("Authorization") String authHeader,
                                   @RequestHeader(value = "Access-Key", required = false) String accessKey, @RequestBody ApiShopSubscribeRequest request, HttpServletRequest httpServletRequest ) {

        ResponseModel response;
        try {
            response =apiShopService.subscribe(request, httpServletRequest);
            response.setRefreshedToken(refreshedToken(authHeader));
        } catch (Exception e) {
            return apiError("/subscribe", getObjectMapper().convertValue(request, JsonNode.class), e);
        }
        return apiLogger("/subscribe", getObjectMapper().convertValue(request, JsonNode.class), response);
    }

    @GetMapping("/settings")
    public ResponseModel settings(@RequestHeader("Authorization") String authHeader, @RequestHeader(value = "Access-Key", required = false) String accessKey,
                                        @RequestParam("shopId") @NotNull Long shopId) {
        ResponseModel response;
        try {
            response = apiShopService.shopSettings(shopId);
            response.setRefreshedToken(refreshedToken(authHeader));
        } catch (Exception e) {
            return apiError("/settings", getObjectMapper().convertValue(null, JsonNode.class), e);
        }
        return apiLogger("/settings", getObjectMapper().convertValue(null, JsonNode.class), response);

    }

    @PostMapping(value = "/update-payment-method")
    public ResponseModel updateShopPaymentMethod(@RequestHeader("Authorization") String authHeader,
                                   @RequestHeader(value = "Access-Key", required = false) String accessKey,
                                                 @RequestBody ApiUpdateShopPaymentMethod request) {

        ResponseModel response;
        try {
            response =apiShopService.updateShopPaymentMethod(request);
            response.setRefreshedToken(refreshedToken(authHeader));
        } catch (Exception e) {
            return apiError("/update-payment-method", getObjectMapper().convertValue(request, JsonNode.class), e);
        }
        return apiLogger("/update-payment-method", getObjectMapper().convertValue(request, JsonNode.class), response);
    }

    @GetMapping("/categories")
    public ResponseModel shopCategories(@RequestHeader("Authorization") String authHeader){
        ResponseModel response;
        try {
            response = apiShopService.shopCategories();
            response.setRefreshedToken(refreshedToken(authHeader));
        } catch (Exception e) {
            return apiError("/categories", getObjectMapper().convertValue(null, JsonNode.class), e);
        }
        return apiLogger("/categories", getObjectMapper().convertValue(null, JsonNode.class), response);
    }


    @GetMapping("/employees")
    public ResponseModel shopEmployees(@RequestHeader("Authorization") String authHeader,
                                   @RequestParam(value = "shopId") @NotNull long shopId) {
        ResponseModel response;
        try {
            response = apiShopService.shopEmployees(shopId);
            response.setRefreshedToken(refreshedToken(authHeader));
        } catch (Exception e) {
            return apiError("/employees", getObjectMapper().convertValue(null, JsonNode.class), e);
        }
        return apiLogger("/employees", getObjectMapper().convertValue(null, JsonNode.class), response);
    }


    @PostMapping("/edit-employee")
    public ResponseModel editEmployee(@RequestHeader("Authorization") String authHeader,
                                      @RequestBody ApiEditEmployeeReq req) {
        ResponseModel response;
        try {
            response = apiShopService.editEmployee(req);
            response.setRefreshedToken(refreshedToken(authHeader));
        } catch (Exception e) {
            return apiError("/edit-employee", getObjectMapper().convertValue(null, JsonNode.class), e);
        }
        return apiLogger("/edit-employee", getObjectMapper().convertValue(null, JsonNode.class), response);
    }


    /**
     * add employee
     * */
    @PostMapping("/add-employee")
    public ResponseModel addEmployee(@RequestHeader("Authorization") String authHeader,
                                      @RequestBody ApiAddEmployeeReq req) {
        ResponseModel response;
        try {
            response = apiShopService.addEmployee(req);
            response.setRefreshedToken(refreshedToken(authHeader));
        } catch (Exception e) {
            return apiError("/add-employee", getObjectMapper().convertValue(null, JsonNode.class), e);
        }
        return apiLogger("/add-employee", getObjectMapper().convertValue(null, JsonNode.class), response);
    }


    @GetMapping("/update-sell-settings")
    public ResponseModel updateSellSetting(@RequestHeader("Authorization") String authHeader, @RequestHeader(value = "Access-Key", required = false) String accessKey,
                                  @RequestParam("shopId") @NotNull Long shopId) {
        ResponseModel response;
        try {
            response = apiShopService.updateSellSetting(shopId);
            response.setRefreshedToken(refreshedToken(authHeader));
        } catch (Exception e) {
            return apiError("/update-sell-settings", getObjectMapper().convertValue(null, JsonNode.class), e);
        }
        return apiLogger("/update-sell-settings", getObjectMapper().convertValue(null, JsonNode.class), response);

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
        return super.apiLogger("/api/v1/shops" + url, filterRequest(request, ""), response);
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
        return super.apiError("/api/v1/shops" + url, filterRequest(request, ""), ex);
    }
}
