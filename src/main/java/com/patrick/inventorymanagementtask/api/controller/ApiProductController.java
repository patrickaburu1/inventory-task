package com.patrick.inventorymanagementtask.api.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.patrick.inventorymanagementtask.api.models.*;
import com.patrick.inventorymanagementtask.api.services.ApiProductService;
import com.patrick.inventorymanagementtask.api.services.ApiShopService;
import com.patrick.inventorymanagementtask.api.utils.ApiAbstractController;
import com.patrick.inventorymanagementtask.security.api.TokenService;
import com.patrick.inventorymanagementtask.utils.AppConstants;
import com.patrick.inventorymanagementtask.utils.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author patrick on 3/31/20
 * @project shop-pos
 */
@RestController
@RequestMapping(value = "/api/v1")
public class ApiProductController extends ApiAbstractController {

    @Autowired
    private ApiProductService apiProductService;
    @Autowired
    private ApiShopService apiShopService;
    @Autowired
    private TokenService tokenService;

    /**
     * add category
     */
    @PostMapping(value = "/add-category")
    public ResponseModel addCategory(@RequestHeader("Authorization") String authHeader,
                                     @RequestHeader(value = "Access-Key", required = false) String accessKey, @RequestBody ApiAddCategoryReq req) {

        ResponseModel response;
        try {
            response = apiProductService.createCategory(req);
            response.setRefreshedToken(refreshedToken(authHeader));
        } catch (Exception e) {
            return apiError("/add-category", getObjectMapper().convertValue(req, JsonNode.class), e);
        }
        return apiLogger("/add-category", getObjectMapper().convertValue(req, JsonNode.class), response);
    }

    /**
     * get categories
     */
    @GetMapping(value = "/categories")
    public ResponseModel getCategories(@RequestHeader("Authorization") String authHeader,
                                       @RequestHeader(value = "Access-Key", required = false) String accessKey, @RequestParam("shopId") @NotNull Long shopId) {
        ResponseModel responseModel;
        try {
            responseModel = apiProductService.shopCategories(shopId);
            responseModel.setRefreshedToken(refreshedToken(authHeader));
        } catch (Exception e) {
            return apiError("/categories", getObjectMapper().convertValue(shopId, JsonNode.class), e);
        }
        return apiLogger("/categories", getObjectMapper().convertValue(shopId, JsonNode.class), responseModel);
    }

    /**
     * add products
     */
    @PostMapping(value = "/add-product")
    public ResponseModel addNewProduct(@RequestHeader("Authorization") String authHeader, @RequestHeader(value = "Access-Key", required = false) String accessKey,
                                       @RequestBody ApiCreateProduct request, HttpServletRequest httpServletRequest ) {
        ResponseModel responseModel;
        try {
            responseModel = apiProductService.createNewProduct(request, httpServletRequest);
            responseModel.setRefreshedToken(refreshedToken(authHeader));
        } catch (Exception e) {
            return apiError("/categories", getObjectMapper().convertValue(request, JsonNode.class), e);
        }
        return apiLogger("/categories", getObjectMapper().convertValue(request, JsonNode.class), responseModel);
    }

    /**
     * get products
     */
    @GetMapping(value = "/products")
    public ResponseModel products(@RequestHeader("Authorization") String authHeader, @RequestHeader(value = "Access-Key", required = false) String accessKey,
                                  @RequestParam("shopId") @NotNull Long shopId,
                                  @RequestParam("page") @NotNull Integer page,
                                  @RequestParam("size") @NotNull Integer size, HttpServletRequest httpServletRequest ) {
        ResponseModel responseModel;
        try {
            responseModel = apiProductService.shopProducts(shopId, page, size,httpServletRequest);
            responseModel.setRefreshedToken(refreshedToken(authHeader));
        } catch (Exception e) {
            return apiError("/products", getObjectMapper().convertValue(shopId, JsonNode.class), e);
        }
        return apiLogger("/products", getObjectMapper().convertValue(shopId, JsonNode.class), responseModel);
    }

    /**
     * get stock
     */
    @GetMapping(value = "/products-stock")
    public ResponseModel stock(@RequestHeader("Authorization") String authHeader, @RequestHeader(value = "Access-Key", required = false) String accessKey,
                                  @RequestParam("shopId") @NotNull Long shopId,
                                  @RequestParam("page") @NotNull Integer page,
                                  @RequestParam("size") @NotNull Integer size, HttpServletRequest httpServletRequest ) {
        ResponseModel responseModel;
        try {
            responseModel = apiProductService.stockProducts(shopId, page, size, httpServletRequest);
            responseModel.setRefreshedToken(refreshedToken(authHeader));
        } catch (Exception e) {
            return apiError("/products-stock", getObjectMapper().convertValue(shopId, JsonNode.class), e);
        }
        return apiLogger("/products-stock", getObjectMapper().convertValue(shopId, JsonNode.class), responseModel);
    }

    /**
     * get stock
     */
    @GetMapping(value = "/products-sell")
    public ResponseModel productsSell(@RequestHeader("Authorization") String authHeader, @RequestHeader(value = "Access-Key", required = false) String accessKey,
                               @RequestParam("shopId") @NotNull Long shopId,
                               @RequestParam("page") @NotNull Integer page,
                               @RequestParam("size") @NotNull Integer size, HttpServletRequest httpServletRequest ) {
        ResponseModel responseModel;
        try {
            responseModel = apiProductService.productsSell(shopId, page, size,httpServletRequest);
            responseModel.setRefreshedToken(refreshedToken(authHeader));
        } catch (Exception e) {
            return apiError("/products-sell", getObjectMapper().convertValue(shopId, JsonNode.class), e);
        }
        return apiLogger("/products-sell", getObjectMapper().convertValue(shopId, JsonNode.class), responseModel);
    }


    /**
     * edit product
     */
    @PostMapping(value = "/edit-product")
    public ResponseModel editProduct(@RequestHeader("Authorization") String authHeader, @RequestHeader(value = "Access-Key", required = false) String accessKey,
                                     @RequestBody ApiEditProductReq request, HttpServletRequest httpServletRequest ) {
        ResponseModel responseModel;
        try {
            responseModel = apiProductService.editProduct(request, httpServletRequest);
            responseModel.setRefreshedToken(refreshedToken(authHeader));
        } catch (Exception e) {
            return apiError("/edit-product", getObjectMapper().convertValue(request, JsonNode.class), e);
        }
        return apiLogger("/edit-product", getObjectMapper().convertValue(request, JsonNode.class), responseModel);
    }

    /**
     * add product stock
     */
    @PostMapping(value = "/add-stock")
    public ResponseModel addStock(@RequestHeader("Authorization") String authHeader, @RequestHeader(value = "Access-Key", required = false) String accessKey,
                                  @Valid @RequestBody ApiAddStockViaInvoiceReq request, HttpServletRequest httpServletRequest ) {
        ResponseModel responseModel;
        try {
            responseModel = apiProductService.addStockViaInvoice(request, httpServletRequest);
            responseModel.setRefreshedToken(refreshedToken(authHeader));
        } catch (Exception e) {
            return apiError("/add-stock", getObjectMapper().convertValue(request, JsonNode.class), e);
        }
        return apiLogger("/add-stock", getObjectMapper().convertValue(request, JsonNode.class), responseModel);
    }

    /**
     * add product stock
     */
    @PostMapping(value = "/remove-stock")
    public ResponseModel removeStock(@RequestHeader("Authorization") String authHeader, @RequestHeader(value = "Access-Key", required = false) String accessKey,
                                     @RequestBody ApiRemoveStock request, HttpServletRequest httpServletRequest ) {
        ResponseModel responseModel;
        try {
            responseModel = apiProductService.removeStock(request, httpServletRequest);
            responseModel.setRefreshedToken(refreshedToken(authHeader));
        } catch (Exception e) {
            return apiError("/remove-stock", getObjectMapper().convertValue(request, JsonNode.class), e);
        }
        return apiLogger("/remove-stock", getObjectMapper().convertValue(request, JsonNode.class), responseModel);
    }

    /**
     * deactivate product
     */
    @PostMapping(value = "/de-activate-product")
    public ResponseModel deActivateProduct(@RequestHeader("Authorization") String authHeader, @RequestHeader(value = "Access-Key", required = false) String accessKey, @RequestBody ApiRemoveStock request) {
        ResponseModel responseModel;
        try {
            responseModel = apiProductService.deActivateProduct(request);
            responseModel.setRefreshedToken(refreshedToken(authHeader));
        } catch (Exception e) {
            return apiError("/de-activate-product", getObjectMapper().convertValue(request, JsonNode.class), e);
        }
        return apiLogger("/de-activate-product", getObjectMapper().convertValue(request, JsonNode.class), responseModel);
    }


    /**
     * add product stock
     */
    @PostMapping(value = "/add-invoice-stock")
    public ResponseModel addStockViaStock(@RequestHeader("Authorization") String authHeader, @RequestHeader(value = "Access-Key", required = false) String accessKey,
                                          @RequestBody ApiAddStockViaInvoiceReq request, HttpServletRequest httpServletRequest ) {
        ResponseModel responseModel = new ResponseModel();
        try {
            responseModel = apiProductService.addStockViaInvoice(request, httpServletRequest);

            responseModel.setRefreshedToken(refreshedToken(authHeader));
        } catch (Exception e) {
            return apiError("/add-invoice-stock", getObjectMapper().convertValue(request, JsonNode.class), e);
        }
        return apiLogger("/add-invoice-stock", getObjectMapper().convertValue(request, JsonNode.class), responseModel);
    }

    /**
     * add product stock
     */
    @PostMapping(value = "/sell")
    public ResponseModel apiSell(@RequestHeader("Authorization") String authHeader, @RequestHeader(value = "Access-Key", required = false) String accessKey,
                              @RequestBody ApiSellRequest request, HttpServletRequest httpServletRequest ) {
        ResponseModel responseModel;
        try {
            responseModel = apiShopService.sell(request, httpServletRequest);

            responseModel.setRefreshedToken(refreshedToken(authHeader));
        } catch (Exception e) {
            return apiError("/sell", getObjectMapper().convertValue(request, JsonNode.class), e);
        }
        return apiLogger("/sell", getObjectMapper().convertValue(request, JsonNode.class), responseModel);
    }

    /**
     * add product stock
     */
    @PostMapping(value = "/pay-debt")
    public ResponseModel apiPayDebt(@RequestHeader("Authorization") String authHeader,
                              @RequestBody ApiClearDebt request, HttpServletRequest httpServletRequest ) {
        ResponseModel responseModel;
        try {
            responseModel = apiShopService.payDebt(request, httpServletRequest);

            responseModel.setRefreshedToken(refreshedToken(authHeader));
        } catch (Exception e) {
            return apiError("/pay-debt", getObjectMapper().convertValue(request, JsonNode.class), e);
        }
        return apiLogger("/pay-debt", getObjectMapper().convertValue(request, JsonNode.class), responseModel);
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
        return super.apiLogger("/api/v1" + url, filterRequest(request, ""), response);
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
        return super.apiError("/api/v1" + url, filterRequest(request, ""), ex);
    }
}
