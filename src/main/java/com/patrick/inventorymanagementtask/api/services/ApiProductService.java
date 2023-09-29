package com.patrick.inventorymanagementtask.api.services;

import com.patrick.inventorymanagementtask.api.models.*;
import com.patrick.inventorymanagementtask.entities.Invoices;
import com.patrick.inventorymanagementtask.entities.products.Category;
import com.patrick.inventorymanagementtask.entities.products.Product;
import com.patrick.inventorymanagementtask.entities.products.Shop;
import com.patrick.inventorymanagementtask.entities.products.Suppliers;
import com.patrick.inventorymanagementtask.entities.user.ShopRights;
import com.patrick.inventorymanagementtask.entities.user.Users;
import com.patrick.inventorymanagementtask.properties.ApplicationMessages;
import com.patrick.inventorymanagementtask.repositories.product.CategoryRepository;
import com.patrick.inventorymanagementtask.repositories.product.ProductRepository;
import com.patrick.inventorymanagementtask.repositories.product.SupplierRepository;
import com.patrick.inventorymanagementtask.security.service.CustomUserDetailsService;
import com.patrick.inventorymanagementtask.service.DashboardService;
import com.patrick.inventorymanagementtask.service.ProductService;
import com.patrick.inventorymanagementtask.service.ShopService;
import com.patrick.inventorymanagementtask.service.StockService;
import com.patrick.inventorymanagementtask.utils.*;
import com.patrick.inventorymanagementtask.utils.ProductComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author patrick on 3/31/20
 * @project shop-pos
 */
@Service
@Transactional
public class ApiProductService {
    @Autowired
    private ProductService productService;
    @Autowired
    private ApiShopService apiShopService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ShopService shopService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private StockService stockService;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private DashboardService dashboardService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * create product
     */
    public ResponseModel createNewProduct(ApiCreateProduct req, HttpServletRequest httpServletRequest) {
        if (null == req.getProduct().getShopId())
            return new ResponseModel("01", "Sorry! You invalid shop.");

        if (!apiShopService.checkIfAllowed(ShopRights.PRIVILEGE_PRODUCTS, req.getProduct().getShopId()))
            return new ResponseModel("01", "Sorry! You are not allowed to perform this operation.");
        Shop shop = shopService.getShop(req.getProduct().getShopId());
        Users user = userDetailsService.getAuthicatedUser();
        dashboardService.saveAuditLogMobile("Add product ", shop.getId(), httpServletRequest);

        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUser(shop, user);
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;
        return productService.uploadProductApi(req);
    }

    /**
     * create shop categories
     */

    public ResponseModel createCategory(ApiAddCategoryReq req) {
        if (null == req.getShopId())
            return new ResponseModel("01", "Sorry! You invalid shop.");
        if (!apiShopService.checkIfAllowed(ShopRights.PRIVILEGE_PRODUCTS, req.getShopId()))
            return new ResponseModel("01", "Sorry! You are not allowed to perform this operation.");

        return productService.addCategory(req.getName(), req.getDescription(), req.getShopId());
    }

    /**
     * shop categories
     */
    public ResponseModel shopCategories(Long shopId) {
        if (!apiShopService.checkIfAllowed(ShopRights.PRIVILEGE_PRODUCTS, shopId))
            return new ResponseModel("01", "Sorry! You are not allowed to perform this operation.");
        Shop shop = shopService.getShop(shopId);
        Users user = userDetailsService.getAuthicatedUser();
        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUser(shop, user);
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;
        return productService.categories(shopId);
    }

    /**
     * shop products
     */
    public ResponseModel shopProducts(Long shopId, Integer page, Integer size, HttpServletRequest httpServletRequest) {
        if (!apiShopService.checkIfAllowed(ShopRights.PRIVILEGE_PRODUCTS, shopId))
            return new ResponseModel("01", "Sorry! You are not allowed to perform this operation.");
        ResponseModel responseModel = shopService.verifyIfValidShop(shopId);
        if (!responseModel.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return responseModel;

        Shop shop = shopService.getShop(shopId);
        Users user = userDetailsService.getAuthicatedUser();

        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUser(shop, user);
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;


        logger.info(String.format("============ api products shop %s page %s size %s ", shopId, page, size));
        page = page != null ? page : 0;
        size = size != null ? size : 20;
        if (page == 0)
            dashboardService.saveAuditLogMobile("Get Products ", shopId, httpServletRequest);

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productRepository.findAllByShopIdAndFlagOrderByNameAsc(shopId, AppConstants.ACTIVE_RECORD, pageable);
        List<ApiProductRes> apiProductRes = new ArrayList<>();
        for (Product product : products) {
            ApiProductRes apiProductRes1 = new ApiProductRes();
         /*   ApiProductRes.LastSupplier lastSupplier = new ApiProductRes.LastSupplier();
            Suppliers getLastSupplier = SystemComponent.getLastSupplier(product.getId());
*/
            BeanUtils.copyProperties(product, apiProductRes1);
           /* if (null != getLastSupplier)
                BeanUtils.copyProperties(getLastSupplier, lastSupplier);

            apiProductRes1.setLastSupplier(lastSupplier);*/
            apiProductRes.add(apiProductRes1);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("products", apiProductRes);
        return new ResponseModel("00", "Success.", response);

    }

    /**
     * sell products
     */
    public ResponseModel productsSell(Long shopId, Integer page, Integer size, HttpServletRequest httpServletRequest) {

        ResponseModel responseModel = shopService.verifyIfValidShop(shopId);
        if (!responseModel.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return responseModel;

        if (!apiShopService.checkIfAllowed(ShopRights.PRIVILEGE_SELL, shopId))
            return new ResponseModel("01", "Sorry! You are not allowed to perform this operation.");

        Shop shop = shopService.getShop(shopId);
        Users user = userDetailsService.getAuthicatedUser();

        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUser(shop, user);
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;


        logger.info(String.format("============ api products sell shop %s page %s size %s ", shopId, page, size));
        page = page != null ? page : 0;
        size = size != null ? size : 20;

        if (page == 0)
            dashboardService.saveAuditLogMobile("Products products to sell", shopId, httpServletRequest);

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productRepository.findAllByShopIdAndFlagOrderByNameAsc(shopId, AppConstants.ACTIVE_RECORD, pageable);
        List<ApiProductRes> apiProductRes = new ArrayList<>();
        for (Product product : products) {
            ApiProductRes apiProductRes1 = new ApiProductRes();
            BeanUtils.copyProperties(product, apiProductRes1);
            apiProductRes.add(apiProductRes1);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("products", apiProductRes);
        return new ResponseModel("00", "Success.", response);

    }

    /**
     * stock products
     */
    public ResponseModel stockProducts(Long shopId, Integer page, Integer size, HttpServletRequest httpServletRequest) {

        ResponseModel responseModel = shopService.verifyIfValidShop(shopId);
        if (!responseModel.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return responseModel;

        if (!apiShopService.checkIfAllowed(ShopRights.PRIVILEGE_STOCK, shopId))
            return new ResponseModel("01", "Sorry! You are not allowed to perform this operation.");

        Shop shop = shopService.getShop(shopId);
        Users user = userDetailsService.getAuthicatedUser();

        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUser(shop, user);
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;



        logger.info(String.format("============ api products shop %s page %s size %s ", shopId, page, size));
        page = page != null ? page : 0;
        size = size != null ? size : 20;

        if (page == 0)
            dashboardService.saveAuditLogMobile("Get Products for stock ", shopId, httpServletRequest);

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productRepository.findAllByShopIdAndFlagOrderByNameAsc(shopId, AppConstants.ACTIVE_RECORD, pageable);
        List<ApiProductRes> apiProductRes = new ArrayList<>();
        for (Product product : products) {
            ApiProductRes apiProductRes1 = new ApiProductRes();
            ApiProductRes.LastSupplier lastSupplier = new ApiProductRes.LastSupplier();
            Suppliers getLastSupplier = SystemComponent.getLastSupplier(product.getId());

            BeanUtils.copyProperties(product, apiProductRes1);
            if (null != getLastSupplier)
                BeanUtils.copyProperties(getLastSupplier, lastSupplier);

            apiProductRes1.setLastSupplier(lastSupplier);
            apiProductRes.add(apiProductRes1);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("products", apiProductRes);
        return new ResponseModel("00", "Success.", response);

    }

    /**
     * edit product
     */
    public ResponseModel editProduct(ApiEditProductReq req, HttpServletRequest httpServletRequest) {
        ResponseModel response = new ResponseModel();
        response.setStatus("01");
        if (null == req.getShopId())
            return new ResponseModel("01", "Sorry! You invalid shop.");
        if (!apiShopService.checkIfAllowed(ShopRights.PRIVILEGE_PRODUCTS, req.getShopId()))
            return new ResponseModel("01", "Sorry! You are not allowed to perform this operation.");

        ResponseModel responseModel = shopService.verifyIfValidShop(req.getShopId());
        if (!responseModel.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return responseModel;

        Shop shop = shopService.getShop(req.getShopId());

        dashboardService.saveAuditLogMobile("Edit product ", shop.getId(), httpServletRequest);

        Users user = userDetailsService.getAuthicatedUser();

        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUser(shop, user);
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;

        Optional<Product> checkProduct = productRepository.findById(req.getId());
        if (!checkProduct.isPresent())
            return new ResponseModel("01", "Sorry product not found for editing");
        Product product = checkProduct.get();
        Category category;
        if (req.getCategoryId() == 0) {
            category = productService.createCategory(req.getName(), shop);
        } else {
            category = categoryRepository.findTopByIdAndShopId(req.getCategoryId(), shop.getId());
        }
        if (null == category)
            return new ResponseModel("01", "Sorry! Invalid product category.");

        response = ProductComponent.validateProductPrices(req.getBuyingPrice(), req.getSellingPrice(), req.getMinSellingPrice());
        if (!response.getStatus().equals(AppConstants.SUCCESS_STATUS_CODE)) {
            return response;
        }

        ProductComponent.trackPriceProductPriceChange(product, shop, user, req.getBuyingPrice(), req.getSellingPrice(), req.getMinSellingPrice(), "Edit", "Prices edited");


        BigDecimal bp = req.getBuyingPrice();
        BigDecimal sp = req.getSellingPrice();
        BigDecimal minSp = req.getMinSellingPrice();

        if (null != req.getCode() && !product.getCode().equalsIgnoreCase(req.getCode()) && !productService.checkExistingProductCode(req.getCode(), shop.getId()).getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return productService.checkExistingProductCode(req.getCode(), shop.getId());

        product.setCategoryId(category.getId())
                .setName(req.getName())
                .setDescription(req.getDescription())
                .setReOrderLevel(req.getReOrderLevel())
                .setBuyingPrice(bp)
                .setSellingPrice(sp)
                .setMinSellingPrice(minSp)
                .setCode(req.getCode());
        productRepository.save(product);

        return new ResponseModel("00", "Success edited product.");
    }

    /**
     * add stock
     */
    public ResponseModel addStock(ApiAddStockReq req) {
        ResponseModel response = new ResponseModel();
        response.setStatus("01");
        if (null == req.getShopId())
            return new ResponseModel("01", "Sorry! You invalid shop.");
        if (!apiShopService.checkIfAllowed(ShopRights.PRIVILEGE_STOCK, req.getShopId()))
            return new ResponseModel("01", "Sorry! You are not allowed to perform this operation.");

        ResponseModel responseModel = shopService.verifyIfValidShop(req.getShopId());
        if (!responseModel.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return responseModel;

        Shop shop = shopService.getShop(req.getShopId());
        Users user = userDetailsService.getAuthicatedUser();

        Optional<Product> checkProduct = productRepository.findById(req.getId());
        if (!checkProduct.isPresent())
            return new ResponseModel("01", "Sorry product not found for editing");
        Product product = checkProduct.get();

        if (req.getSellingPrice().compareTo(req.getBuyingPrice()) < 0) {
            response.setMessage(ApplicationMessages.get("response.product.low.sp"));
            return response;
        } else if (req.getMinSellingPrice().compareTo(req.getBuyingPrice()) < 0) {
            response.setMessage(ApplicationMessages.get("response.product.low.minsp"));
            return response;
        }

        Suppliers supplier = null;
        if (null == req.getSuppierId() || req.getSuppierId() == 0) {
            supplier = productService.createSupplier(req.getSupplierName(), shop, user);
        } else if (null != req.getSuppierId() && req.getSuppierId() > 0) {
            supplier = supplierRepository.findFirstByIdAndShopId(req.getSuppierId(), shop.getId());
        }
        if (null == supplier)
            return new ResponseModel("01", ApplicationMessages.get("response.product.invalid.supplier"));

        return productService.addProductStock(product, supplier, shop, req.getQuantity(), req.getBuyingPrice(), req.getSellingPrice(), req.getMinSellingPrice(), null, user);
    }

    /**
     * remove stock
     */
    public ResponseModel removeStock(ApiRemoveStock request, HttpServletRequest httpServletRequest) {
        ResponseModel response = new ResponseModel();
        response.setStatus("01");

        Users users = userDetailsService.getAuthicatedUser();
        if (null == request.getShopId())
            return new ResponseModel("01", "Sorry! You invalid shop.");
        if (!apiShopService.checkIfAllowed(ShopRights.PRIVILEGE_STOCK, request.getShopId()))
            return new ResponseModel("01", "Sorry! You are not allowed to perform this operation.");

        ResponseModel responseModel = shopService.verifyIfValidShop(request.getShopId());
        if (!responseModel.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return responseModel;

        Shop shop = shopService.getShop(request.getShopId());
        Users user = userDetailsService.getAuthicatedUser();

        dashboardService.saveAuditLogMobile("Remove stock ", shop.getId(), httpServletRequest);

        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUser(shop, user);
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;


        if (request.getQuantity() <= 0) {
            response.setMessage("Sorry invalid quantity.");
            return response;
        }
        Optional<Product> validProduct = this.productRepository.findByIdAndShopId(request.getProductId(), shop.getId());

        //verify user password
        /*if (!productService.verifyPassword(request.getPassword(), users)) {
            response.setMessage("Sorry! Invalid password.");
            return response;
        }*/

        if (!validProduct.isPresent()) {
            response.setMessage("Sorry! Product not found");
            return response;
        }
        Product product = validProduct.get();

        if (request.getQuantity() > product.getStock()) {
            response.setMessage("Sorry quantity if more than available stock.");
            return response;
        }
        if (null == request.getReason() || request.getReason().isEmpty())
            return new ResponseModel("01", "Reason for removing stock is required.");

        ProductComponent.trackProductTrailDeduct(product, shop, users, request.getQuantity(), "Remove-Stock", request.getReason());
        //remove stock
        product.setStock(product.getStock() - request.getQuantity());
        productRepository.save(product);
        String logMessage = String.format("Removed %s of %s from stock ", request.getQuantity(), product.getName());
        stockService.log(users, logMessage);
        response.setStatus("00");
        response.setMessage("Successfully removed  stock.");
        return response;
    }


    /**
     * delete product
     */
    public ResponseModel deActivateProduct(ApiRemoveStock request) {
        ResponseModel response = new ResponseModel();
        response.setStatus("01");

        Users users = userDetailsService.getAuthicatedUser();
        if (null == request.getShopId())
            return new ResponseModel("01", "Sorry! You invalid shop.");
        if (!apiShopService.checkIfAllowed(ShopRights.PRIVILEGE_PRODUCTS, request.getShopId()))
            return new ResponseModel("01", "Sorry! You are not allowed to perform this operation.");

        ResponseModel responseModel = shopService.verifyIfValidShop(request.getShopId());
        if (!responseModel.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return responseModel;

        Shop shop = shopService.getShop(request.getShopId());

        Users user = userDetailsService.getAuthicatedUser();

        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUser(shop, user);
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;

        Optional<Product> validProduct = this.productRepository.findByIdAndShopId(request.getProductId(), shop.getId());
        //verify user password
        /*if (!productService.verifyPassword(request.getPassword(), users)) {
            response.setMessage("Sorry! Invalid password.");
            return response;
        }*/
        if (!validProduct.isPresent()) {
            response.setMessage("Sorry we are unable to process your request at the moment");
            return response;
        }
        Product product = validProduct.get();
        product.setFlag(AppConstants.SOFT_DELETED);
        productRepository.save(product);
        String logMessage = String.format("Deactivate product %s  ", product.getName());
        stockService.log(users, logMessage);

        return new ResponseModel("00", String.format("Success fully deactivated %s", product.getName()));
    }

    /**
     * add stock
     */
    public ResponseModel addStockViaInvoice(ApiAddStockViaInvoiceReq req, HttpServletRequest httpServletRequest) {
        ResponseModel response = new ResponseModel();
        response.setStatus("01");
        if (null == req.getShopId())
            return new ResponseModel("01", "Sorry! You invalid shop.");
        //
        if (!apiShopService.checkIfAllowed(ShopRights.PRIVILEGE_STOCK, req.getShopId()))
            return new ResponseModel("01", "Sorry! You are not allowed to perform this operation.");

        ResponseModel responseModel = shopService.verifyIfValidShop(req.getShopId());
        if (!responseModel.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return responseModel;

        Shop shop = shopService.getShop(req.getShopId());
        Users user = userDetailsService.getAuthicatedUser();

        dashboardService.saveAuditLogMobile("Add stock via invoice", shop.getId(), httpServletRequest);


        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUser(shop, user);
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;

        //validate products
        for (ApiAddStockViaInvoiceReq.ApiAddStockViaInvoiceReqStock stock : req.getStock()) {
            Optional<Product> checkProduct = productRepository.findFirstByIdAndShopId(stock.getProductId(), shop.getId());
            if (!checkProduct.isPresent())
                return new ResponseModel("01", "Sorry product not found ");
            Product product = checkProduct.get();

            response = ProductComponent.validateProductPricesWithProductName(product, stock.getBuyingPrice(), stock.getSellingPrice(), stock.getMinSellingPrice());
            if (!response.getStatus().equals(AppConstants.SUCCESS_STATUS_CODE))
                return response;

            if (stock.getQuantity() <= 0)
                return new ResponseModel("01", String.format("Sorry! Invalid quantity %s for product %s.", stock.getQuantity(), product.getName()));

            if (stock.getSupplier().getSupplierId() != 0) {
                Suppliers supplier = supplierRepository.findFirstByIdAndShopId(stock.getSupplier().getSupplierId(), shop.getId());
                if (null == supplier) {
                    response.setMessage(String.format("Sorry! Invalid supplier for product %s.", product.getName()));
                    return response;
                }
            }
        }

        for (ApiAddStockViaInvoiceReq.ApiAddStockViaInvoiceReqStock stock : req.getStock()) {
            Optional<Product> checkProduct = productRepository.findFirstByIdAndShopId(stock.getProductId(), shop.getId());
            Product product = checkProduct.get();

            Suppliers supplier = null;
            if (null == stock.getSupplier().getSupplierId() || stock.getSupplier().getSupplierId() == 0) {
                supplier = productService.createSupplier(stock.getSupplier().getName(), shop, user);
            } else if (null != stock.getSupplier().getSupplierId() && stock.getSupplier().getSupplierId() > 0) {
                supplier = supplierRepository.findFirstByIdAndShopId(stock.getSupplier().getSupplierId(), shop.getId());
            }
            Invoices invoice = null;
            if (null != stock.getInvoice() && null != stock.getInvoice().getInvoiceNo() && !stock.getInvoice().getInvoiceNo().isEmpty()) {
                String invoiceStatus;
                if (stock.getInvoice().getStatus())
                    invoiceStatus = AppConstants.INVOICE_PAID;
                else
                    invoiceStatus = AppConstants.INVOICE_UN_PAID;

                invoice = productService.checkIfInvoiceExistsAndCreate(stock.getInvoice().getInvoiceNo(), stock.getInvoice().getDate(), invoiceStatus, shop, supplier, user);
            }


            productService.addProductStock(product, supplier, shop, stock.getQuantity(), stock.getBuyingPrice(), stock.getSellingPrice(), stock.getMinSellingPrice(), invoice, user);
        }

        return new ResponseModel("00", "Successfully added stock.");
    }
}
