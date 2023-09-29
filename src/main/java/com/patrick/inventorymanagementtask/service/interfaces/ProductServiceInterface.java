package com.patrick.inventorymanagementtask.service.interfaces;

import com.patrick.inventorymanagementtask.models.AddProductReq;
import com.patrick.inventorymanagementtask.models.AddToCartReq;
import com.patrick.inventorymanagementtask.models.WebCompleteSellReq;
import com.patrick.inventorymanagementtask.utils.ResponseModel;

import java.text.ParseException;
import java.util.Map;

public interface ProductServiceInterface {

    Map<String, Object> categories();

    ResponseModel categories(Long shopId);

    Map<String, Object> getSellInfo();

    ResponseModel uploadProduct(AddProductReq addProductReq);

    ResponseModel addProductToCart(String productId);

    ResponseModel addProductToCartBarcode(String code);

    ResponseModel addProductToCartMore(AddToCartReq req);

    ResponseModel removeProductFromCart(String checkOuListId);

    ResponseModel  completeSell(WebCompleteSellReq req);

    ResponseModel editProduct(String productId, String name, String bp, String sp,String minSp, String reOrderLevel,String description, String category, String code) throws ParseException;

    ResponseModel addCategory(String name, String description,Long shopId);

    Map<String,Object> deleteProduct(String productId,String password);


    Map<String,Object> deleteTransaction(String transactionId,String password);
}
