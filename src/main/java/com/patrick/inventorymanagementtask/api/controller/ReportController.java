package com.patrick.inventorymanagementtask.api.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.patrick.inventorymanagementtask.api.services.ApiReportsService;
import com.patrick.inventorymanagementtask.api.utils.ApiAbstractController;
import com.patrick.inventorymanagementtask.security.api.TokenService;
import com.patrick.inventorymanagementtask.utils.AppConstants;
import com.patrick.inventorymanagementtask.utils.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

/**
 * @author patrick on 5/23/20
 * @project inventory
 */
@RestController
@RequestMapping(value = "/api/v1/reports")
public class ReportController extends ApiAbstractController {

    @Autowired
    private ApiReportsService apiReportsService;
    @Autowired
    private TokenService tokenService;

    @GetMapping("/summary")
    public ResponseModel apiSummary(@RequestHeader("Authorization") String authHeader, @RequestHeader(value = "Access-Key", required = false) String accessKey,
                                       @RequestParam("shopId") @NotNull Long shopId,
                                    @RequestParam("startDate") @NotNull String startDate,
                                    @RequestParam("endDate") @NotNull String endDate, HttpServletRequest httpServletRequest ) {
        ResponseModel response;
        try {
            response = apiReportsService.summaryReports(shopId, startDate, endDate, httpServletRequest);
            response.setRefreshedToken(refreshedToken(authHeader));
        } catch (Exception e) {
            return apiError("/summary", getObjectMapper().convertValue(null, JsonNode.class), e);
        }
        return apiLogger("/summary", getObjectMapper().convertValue(null, JsonNode.class), response);

    }

    @GetMapping("/sales")
    public ResponseModel apiSalesReport(@RequestHeader("Authorization") String authHeader,
                                    @RequestParam("shopId") @NotNull Long shopId,
                                    @RequestParam("page") @NotNull Integer page,
                                    @RequestParam("size") @NotNull Integer size, HttpServletRequest httpServletRequest ){
        ResponseModel response;
        try {
            response = apiReportsService.salesReport(shopId,page,size,httpServletRequest);
            response.setRefreshedToken(refreshedToken(authHeader));
        } catch (Exception e) {
            return apiError("/sales", getObjectMapper().convertValue(null, JsonNode.class), e);
        }
        return apiLogger("/sales", getObjectMapper().convertValue(null, JsonNode.class), response);

    }

    @GetMapping("/sales/export")
    public ResponseModel salesReportExport(@RequestHeader("Authorization") String authHeader,
                                        @RequestParam("shopId") @NotNull Long shopId,
                                        @RequestParam("startDate") @NotNull String startDate,
                                        @RequestParam("endDate") @NotNull String endDate,
                                      HttpServletRequest httpServletRequest ){
        ResponseModel response;
        try {
            response = apiReportsService.salesReportExport(shopId,startDate,endDate,httpServletRequest);
            response.setRefreshedToken(refreshedToken(authHeader));
        } catch (Exception e) {
            return apiError("/sales/export", getObjectMapper().convertValue(null, JsonNode.class), e);
        }
        return apiLogger("/sales/export", getObjectMapper().convertValue(null, JsonNode.class), response);

    }

    @GetMapping("/expenses")
    public ResponseModel apiExpensesReport(@RequestHeader("Authorization") String authHeader,
                                        @RequestParam("shopId") @NotNull Long shopId,
                                        @RequestParam("page") @NotNull Integer page,
                                        @RequestParam("size") @NotNull Integer size, HttpServletRequest httpServletRequest ){
        ResponseModel response;
        try {
            response = apiReportsService.expensesReport(shopId,page,size, httpServletRequest);
            response.setRefreshedToken(refreshedToken(authHeader));
        } catch (Exception e) {
            return apiError("/expenses", getObjectMapper().convertValue(null, JsonNode.class), e);
        }
        return apiLogger("/expenses", getObjectMapper().convertValue(null, JsonNode.class), response);

    }

    @GetMapping("/debtors")
    public ResponseModel apiDebtors(@RequestHeader("Authorization") String authHeader,
                                           @RequestParam("shopId") @NotNull Long shopId,
                                           @RequestParam("page") @NotNull Integer page,
                                           @RequestParam("size") @NotNull Integer size, HttpServletRequest httpServletRequest ){
        ResponseModel response;
        try {
            response = apiReportsService.getDebtorsReport(shopId,page,size, httpServletRequest);
            response.setRefreshedToken(refreshedToken(authHeader));
        } catch (Exception e) {
            return apiError("/debtors", getObjectMapper().convertValue(null, JsonNode.class), e);
        }
        return apiLogger("/debtors", getObjectMapper().convertValue(null, JsonNode.class), response);

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
        return super.apiLogger("/api/v1/reports" + url, filterRequest(request, ""), response);
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
        return super.apiError("/api/v1/reports" + url, filterRequest(request, ""), ex);
    }
}
