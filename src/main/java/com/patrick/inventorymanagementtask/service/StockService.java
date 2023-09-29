package com.patrick.inventorymanagementtask.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.patrick.inventorymanagementtask.entities.products.*;
import com.patrick.inventorymanagementtask.entities.user.AuditLog;
import com.patrick.inventorymanagementtask.entities.user.UserRole;
import com.patrick.inventorymanagementtask.entities.user.Users;
import com.patrick.inventorymanagementtask.models.ApiAddStockReq;
import com.patrick.inventorymanagementtask.models.products.RemoveStockRequest;
import com.patrick.inventorymanagementtask.models.products.SupplierRequest;
import com.patrick.inventorymanagementtask.properties.ApplicationMessages;
import com.patrick.inventorymanagementtask.repositories.AuditLogRepository;
import com.patrick.inventorymanagementtask.repositories.product.CustomerRepository;
import com.patrick.inventorymanagementtask.repositories.product.ProductRepository;
import com.patrick.inventorymanagementtask.repositories.product.StockRepository;
import com.patrick.inventorymanagementtask.repositories.product.SupplierRepository;
import com.patrick.inventorymanagementtask.repositories.user.UserRepository;
import com.patrick.inventorymanagementtask.repositories.user.UserRoleRepository;
import com.patrick.inventorymanagementtask.security.service.CustomUserDetailsService;
import com.patrick.inventorymanagementtask.service.interfaces.StockServiceInterface;
import com.patrick.inventorymanagementtask.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
@Transactional
public class StockService implements StockServiceInterface {
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuditLogRepository auditLogRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private HttpServletRequest _request;
    @Autowired
    private ShopService shopService;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private AppFunctions appFunctions;


    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Map<String, Object> getSupplier() {
        Map<String, Object> response = new HashMap<>();

        long shopId = Long.parseLong(_request.getSession().getAttribute("__shopId").toString());

        List<Suppliers> suppliers = supplierRepository.getAllByShopId(shopId);

        response.put("status", "00");
        response.put("suppliers", suppliers);
        return response;
    }

    @Override
    public ResponseModel createSupplier(SupplierRequest request) {
        ResponseModel response = new ResponseModel();
        response.setStatus("01");
        Users users = userDetailsService.getAuthicatedUser();
        UserRole userRole = userRoleRepository.findDistinctTopByUserId(users.getId());
        Suppliers supplier = new Suppliers();

        response.setMessage("Successfully added new supplier.");

        long shopId = Long.parseLong(_request.getSession().getAttribute("__shopId").toString());

        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUserWeb();
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;

        //check phone
        if (!appFunctions.validatePhoneNumber(request.getPhone()))
            return new ResponseModel("01", "Sorry! Invalid phone number.");

        request.setPhone(appFunctions.getInternationalPhoneNumber(request.getPhone(), ""));

        if (null != request.getId() && 0 != request.getId()) {
            Optional<Suppliers> checksupplier = supplierRepository.findById(request.getId());

            if (checksupplier.isPresent() && !checksupplier.get().getPhone().equalsIgnoreCase(request.getPhone())) {
                Suppliers checkSupplierWithSamePhone = supplierRepository.findFirstByPhoneAndShopId(request.getPhone(), shopId);
                if (null != checkSupplierWithSamePhone)
                    return new ResponseModel("01", "Failed to updated, Supplier with similar phone number already exists.");
                response.setMessage("Successfully updated supplier details.");
            } else if (checksupplier.isPresent()) {
                response.setMessage("Successfully updated supplier details.");
            } else {
                response.setMessage("Failed to updated supplier details.");
                return response;
            }

            supplier = checksupplier.get();

        } else {
            Suppliers checkSupplier = supplierRepository.findFirstByPhoneAndShopId(request.getPhone(), shopId);
            if (null != checkSupplier)
                return new ResponseModel("01", "Sorry supplier with similar phone already exists");
        }

        supplier
                .setName(request.getName())
                .setPhone(request.getPhone())
                .setAddress(request.getAddress())
                .setDescription(request.getDescription())
                .setMpesaPhone(request.getMpesaNumber())
                .setBank(request.getBank() != null ? request.getBank() : "N/A")
                .setBankAccount(request.getBankAccount() != null ? request.getBankAccount() : "N/A")
                .setEmail(request.getEmail())
                .setShopId(shopId)
                .setCreatedBy(users.getId());

        supplierRepository.save(supplier);

        response.setStatus("00");

        return response;
    }

    @Override
    public Map<String, Object> getStockSuppliers() {

        Map<String, Object> response = new HashMap<>();

        long shopId = Long.parseLong(_request.getSession().getAttribute("__shopId").toString());

        List<Suppliers> suppliers = supplierRepository.getAllByShopId(shopId);

        response.put("status", "00");
        //response.put("products", products);
        response.put("suppliers", suppliers);
        return response;

    }

    @Override
    public Map<String, Object> products() {

        Map<String, Object> response = new HashMap<>();

        long shopId = Long.parseLong(_request.getSession().getAttribute("__shopId").toString());

        List<Suppliers> suppliers = supplierRepository.getAllByShopId(shopId);

        response.put("status", "00");
        // response.put("products", products);
        response.put("suppliers", suppliers);
        return response;

    }

    @Override
    public ResponseModel addStock(ApiAddStockReq req) {

        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(req);
            logger.info("*************** web add to stock req {} ->  " + json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Users users = userDetailsService.getAuthicatedUser();
        UserRole userRole = userRoleRepository.findDistinctTopByUserId(users.getId());
        if (req.getQuantity() <= 0) {
            return new ResponseModel("01", "Sorry! Invalid quantity.");
        }
        ResponseModel responseModel;
        responseModel = shopService.verifyIfValidShop(req.getShopId());
        if (!responseModel.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return responseModel;

        Optional<Product> validProduct = this.productRepository.findFirstByIdAndShopId(req.getProductId(), req.getShopId());
        if (!validProduct.isPresent())
            return new ResponseModel("01", ApplicationMessages.get("response.invalid.product"));

        Product product = validProduct.get();

        responseModel = ProductComponent.validateProductPrices(req.getBuyingPrice(), req.getSellingPrice(), req.getMinSellingPrice());
        if (!responseModel.getStatus().equals(AppConstants.SUCCESS_STATUS_CODE))
            return responseModel;

        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUserWeb();
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;
        Shop shop = shopService.getShop(req.getShopId());
        Suppliers supplier = supplierRepository.findFirstByIdAndShopId(req.getSupplier(), shop.getId());

        if (null == supplier)
            return new ResponseModel("01", "Sorry! Invalid supplier");
        return productService.addProductStock(product, supplier, shop, req.getQuantity(), req.getBuyingPrice(), req.getSellingPrice(), req.getMinSellingPrice(), null, users);
        //sore stock
        /*Stock stock = new Stock();
        stock
                .setBuyingPrice(req.getBuyingPrice())
                .setLastStock(product.getStock())
                .setQuantity(req.getQuantity())
                .setSupplierId(req.getSupplier())
                .setProductId(product.getId())
                .setShopId(req.getShopId());

        stockRepository.save(stock);
        *//*update stock*//*
        product.setStock(product.getStock() + req.getQuantity());

        this.productRepository.save(product);
        response.put("status", "00");
        response.put("message", "Successfully, added Stock.");
        return new ResponseModel("00", "Successfully, added Stock.");*/
    }

    @Override
    public Map<String, Object> getCustomers() {

        Map<String, Object> response = new HashMap<>();


        Iterable<Customer> customers = this.customerRepository.findAllByShopId(shopService.getShopId());

        response.put("status", "00");
        response.put("customers", customers);
        return response;
    }

    @Override
    public Map<String, Object> getStockOut() {

        Map<String, Object> response = new HashMap<>();

        List<Product> products = this.productRepository.getStockOutProductsByFlag(AppConstants.ACTIVE_RECORD, shopService.getShopId());
        List<Suppliers> suppliers = supplierRepository.getAllBy();

        response.put("status", "00");
        response.put("products", products);
        return response;

    }


    @Override
    public Map<String, Object> removeStock(RemoveStockRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "01");

        Users users = userDetailsService.getAuthicatedUser();
        UserRole userRole = userRoleRepository.findDistinctTopByUserId(users.getId());


        if (request.getQuantity() <= 0) {
            response.put("message", "Sorry invalid quantity");
            return response;
        }
        if (null == request.getProductId()) {
            response.put("message", "Invalid product to remove stock from.");
            return response;
        }
        Optional<Product> validProduct = this.productRepository.findById(request.getProductId());

        //verify user password
        if (!productService.verifyPassword(request.getPassword(), users)) {
            response.put("message", "Sorry! Invalid password.");
            return response;
        }

        if (!validProduct.isPresent()) {
            response.put("message", "Sorry we are unable to process your request at the moment");
            return response;
        }

        Long shopId = shopService.getShopId();
        Shop shop = shopService.getShop(shopId);

        Product product = validProduct.get();

        if (request.getQuantity() > product.getStock()) {
            response.put("message", "Sorry quantity if more than available stock.");
            return response;
        }

        // log product removal
        ProductComponent.trackProductTrailDeduct(product, shop, users, request.getQuantity(), "Remove-Stock", request.getReason());

        //remove stock
        product.setStock(product.getStock() - request.getQuantity());
        productRepository.save(product);

        String logMessage = String.format("Remove %s of %s from stock ", request.getQuantity(), product.getName());
        log(users, logMessage);

        response.put("status", "00");
        response.put("message", "Successfully remove product from stock.");

        return response;
    }


    public void log(Users user, String message) {

        AuditLog auditLog = new AuditLog();
        auditLog
                .setCreatedBy(user.getId())
                .setCreatedOn(new Date())
                .setDescription(message)
                .setFlag(AppConstants.ACTIVE_RECORD)
                .setUpdatedOn(new Date());
        auditLogRepository.save(auditLog);
    }

    @Override
    public ResponseModel getSupplierDetail(Integer supplierId) {
        Users users = userDetailsService.getAuthicatedUser();
        Long shopId = shopService.getShopId();
        ResponseModel responseModel;
        responseModel = shopService.verifyIfValidShop(shopId);
        if (!responseModel.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return responseModel;

        Suppliers supplier = supplierRepository.findFirstByIdAndShopId(supplierId, shopId);

        if (null == supplier)
            return new ResponseModel("01", "Invalid supplier");

        return new ResponseModel("00", "success");
    }
}
