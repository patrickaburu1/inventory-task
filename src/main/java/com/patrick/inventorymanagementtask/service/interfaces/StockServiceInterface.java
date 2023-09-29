package com.patrick.inventorymanagementtask.service.interfaces;

import com.patrick.inventorymanagementtask.models.ApiAddStockReq;
import com.patrick.inventorymanagementtask.models.products.RemoveStockRequest;
import com.patrick.inventorymanagementtask.models.products.SupplierRequest;
import com.patrick.inventorymanagementtask.utils.ResponseModel;

import java.util.Map;

public interface StockServiceInterface {

    Map<String, Object> getSupplier();

   ResponseModel createSupplier(SupplierRequest request);

    Map<String, Object> getStockSuppliers();

    Map<String, Object> products();

    ResponseModel addStock(ApiAddStockReq req);


    Map<String, Object> getCustomers();

    Map<String, Object> getStockOut();


    Map<String, Object> removeStock(RemoveStockRequest request);

     ResponseModel getSupplierDetail(Integer supplierId) ;
}
