package com.patrick.inventorymanagementtask.service;

import com.patrick.inventorymanagementtask.api.models.ApiCreateProduct;
import com.patrick.inventorymanagementtask.api.models.ApiSellRequest;
import com.patrick.inventorymanagementtask.config.AppSettingService;
import com.patrick.inventorymanagementtask.entities.*;
import com.patrick.inventorymanagementtask.entities.configs.CreditPayments;
import com.patrick.inventorymanagementtask.entities.products.*;
import com.patrick.inventorymanagementtask.entities.user.UserRole;
import com.patrick.inventorymanagementtask.entities.user.Users;
import com.patrick.inventorymanagementtask.models.AddProductReq;
import com.patrick.inventorymanagementtask.models.AddToCartReq;
import com.patrick.inventorymanagementtask.models.WebCompleteSellReq;
import com.patrick.inventorymanagementtask.models.products.CheckOutResponse;
import com.patrick.inventorymanagementtask.repositories.*;
import com.patrick.inventorymanagementtask.repositories.product.*;
import com.patrick.inventorymanagementtask.repositories.user.UserRepository;
import com.patrick.inventorymanagementtask.repositories.user.UserRoleRepository;
import com.patrick.inventorymanagementtask.security.service.CustomUserDetailsService;
import com.patrick.inventorymanagementtask.service.interfaces.ProductServiceInterface;
import com.patrick.inventorymanagementtask.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
@Transactional
public class ProductService implements ProductServiceInterface {
    @Autowired
    SalesTransactionPaymentsRepository salesTransactionPaymentsRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CheckOutRepository checkOutRepository;
    @Autowired
    private CheckOutListRepository checkOutListRepository;
    @Autowired
    private SalesRepository salesRepository;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PaymentMethodsRepository paymentMethodsRepository;
    @Autowired
    private HttpServletRequest _request;
    @Autowired
    private ShopService shopService;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private SalesTransactionsRepository salesTransactionsRepository;
    @Autowired
    private AppFunctions appFunctions;
    @Autowired
    private InvoicesRepository invoicesRepository;
    @Autowired
    private ShopPaymentMethodsRepository shopPaymentMethodsRepository;
    @Autowired
    private CreditsRepository creditsRepository;
    @Autowired
    private CreditPaymentsRepository creditPaymentsRepository;
    @Autowired
    private AppSettingService appSettingService;

    @Value("${receipt.url}")
    private String receipt_url;

    private Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public Map<String, Object> categories() {
        Map<String, Object> response = new HashMap<>();
        Iterable<Category> categories = this.categoryRepository.findAllByShopId(shopService.getShopId());
        response.put("categories", categories);
        return response;
    }

    @Override
    public ResponseModel categories(Long shopId) {
        Iterable<Category> categories = this.categoryRepository.findAllByShopId(shopId);
        Iterable<Suppliers> suppliers = this.supplierRepository.getAllByShopId(shopId);
        Map<String, Object> res = new HashMap<>();
        res.put("categories", categories);
        res.put("suppliers", suppliers);
        return new ResponseModel(AppConstants.SUCCESS_STATUS_CODE, "Success", res);
    }

    @Override
    public Map<String, Object> getSellInfo() {
        Map<String, Object> response = new HashMap<>();

        Users user = userDetailsService.getAuthicatedUser();
        if (null == user) {
            response.put("status", "01");
            response.put("message", "un- authorized.");
            return response;
        }
        int userId = user.getId();

        long shopId = Long.parseLong(_request.getSession().getAttribute("__shopId").toString());
        List<ShopPaymentMethods> shopPaymentMethods = shopPaymentMethodsRepository.findAllByShopIdAndFlagAndActive(shopId, AppConstants.ACTIVE_RECORD, true);

        List<PaymentMethods> paymentMethods = new ArrayList<>();
        for (ShopPaymentMethods shopPaymentMethod : shopPaymentMethods) {
            PaymentMethods paymentMethod = paymentMethodsRepository.findById(shopPaymentMethod.getPaymentMethodId()).get();
            if (paymentMethod.getCode().equalsIgnoreCase(PaymentMethods.CASH))
                paymentMethod.setChecked(true);
            paymentMethods.add(paymentMethod);
        }

        response.put("paymentModes", paymentMethods);
        response.put("shopId", shopId);

        return response;
    }

    public ResponseModel cartData() {
        ResponseModel responseModel = new ResponseModel();
        Users user = userDetailsService.getAuthicatedUser();
        if (null == user) {
            responseModel.setStatus("01");
            responseModel.setMessage("un- authorized.");
            return responseModel;
        }
        int userId = user.getId();
        long shopId = Long.parseLong(_request.getSession().getAttribute("__shopId").toString());

        BigDecimal grandTotal = BigDecimal.ZERO;
        CheckOut checkOut = null;
        checkOut = this.checkOutRepository.findDistinctTopByCreatedByAndFlagAndShopId(userId, AppConstants.ACTIVE_RECORD, shopId);

        List<CheckOutList> checkOutList = new ArrayList<>();

        if (null != checkOut) {
            checkOutList = this.checkOutListRepository.findAllByCreatedByAndCheckOutIdAndFlag(userId, checkOut.getId(), AppConstants.ACTIVE_RECORD);
            grandTotal = this.checkOutListRepository.getSumTopPay(userId, checkOut.getId(), AppConstants.ACTIVE_RECORD) != null ?
                    this.checkOutListRepository.getSumTopPay(userId, checkOut.getId(), AppConstants.ACTIVE_RECORD) : BigDecimal.valueOf(0);

        }
        List<CheckOutResponse> checkOutResponse = new ArrayList<>();

        for (CheckOutList list : checkOutList) {
            CheckOutResponse check = new CheckOutResponse();
            check
                    .setId(list.getId())
                    .setProduct(productRepository.findById(list.getProductId()).get().getName())
                    .setQuantity(list.getQuantity())
                    .setSellingPrice(list.getSellingPrice())
                    .setTotal(list.getTotal());
            checkOutResponse.add(check);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("checkOutList", checkOutResponse);
        data.put("grandTotal", grandTotal.setScale(0, RoundingMode.UP));

        responseModel.setMessage("Success");
        responseModel.setStatus("00");
        responseModel.setData(data);
        return responseModel;
    }

    public ResponseModel clearCartData() {
        ResponseModel responseModel = new ResponseModel();
        Users user = userDetailsService.getAuthicatedUser();
        if (null == user) {
            responseModel.setStatus("01");
            responseModel.setMessage("un- authorized.");
            return responseModel;
        }
        int userId = user.getId();
        long shopId = Long.parseLong(_request.getSession().getAttribute("__shopId").toString());

        CheckOut checkOut = this.checkOutRepository.findDistinctTopByCreatedByAndFlagAndShopId(userId, AppConstants.ACTIVE_RECORD, shopId);

        if (null == checkOut)
            return new ResponseModel("01", "Sorry! Your cart is empty");

        List<CheckOutList> validCheckOutList = checkOutListRepository.findAllByCreatedByAndCheckOutIdAndFlag(userId, checkOut.getId(), AppConstants.ACTIVE_RECORD);
        if (validCheckOutList.size() == 0)
            return new ResponseModel("01", "Sorry! Your cart is empty");

        for (CheckOutList checkOutList : validCheckOutList) {
            Product validProduct = this.productRepository.findById(checkOutList.getProductId()).get();

            /*update stock*/
            validProduct.setStock(validProduct.getStock() + checkOutList.getQuantity());
            this.productRepository.save(validProduct);

            checkOutList.setFlag(AppConstants.SOFT_DELETED);
            this.checkOutListRepository.save(checkOutList);
        }
        checkOut.setFlag(AppConstants.CART_CLEARED);
        checkOutRepository.save(checkOut);
        clearUserCart(checkOut, validCheckOutList);

        return new ResponseModel("00", "Success, cart cleared.");
    }

    @Override
    public ResponseModel uploadProduct(AddProductReq req) {
        ResponseModel response = new ResponseModel();

        String code = "P";

        response = ProductComponent.validateProductPrices(req.getBuyingPrice(), req.getSellingPrice(), req.getMinSellingPrice());
        if (!response.getStatus().equals(AppConstants.SUCCESS_STATUS_CODE)) {
            return response;
        }
        response.setStatus("01");

        if (null == req.getShopId()) {
            req.setShopId(shopService.getShopId());
        }
        if (!shopService.verifyIfValidShop(req.getShopId()).getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return shopService.verifyIfValidShop(req.getShopId());

        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUserWeb();
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;

        if (!categoryRepository.findById(req.getCategory()).isPresent())
            return new ResponseModel("01", "Sorry! Invalid product category.");
        Product product = new Product();
        product
                .setName(req.getName().toUpperCase())
                .setCode(code)
                .setCategoryId(req.getCategory())
                .setBuyingPrice(req.getBuyingPrice())
                .setDescription(req.getDescription())
                .setStock(req.getCurrentStock())
                .setSellingPrice(req.getSellingPrice())
                .setMinSellingPrice(req.getMinSellingPrice())
                .setReOrderLevel(req.getReOrderLevel())
                .setShopId(req.getShopId());

        product = productRepository.save(product);

        if (null != req.getCode() && !req.getCode().isEmpty()) {
            product.setCode(req.getCode());
        } else {
            product.setCode(product.getCode() + product.getId());
        }

        product = productRepository.save(product);

        if (null != product) {
            response.setStatus("00");
            response.setMessage("Successfully added product.");
        } else {
            response.setStatus("01");
            response.setMessage("Failed, please try again later.");
        }

        return response;
    }


    public ResponseModel uploadProductApi(ApiCreateProduct req) {
        ResponseModel response = new ResponseModel();
        response.setStatus("01");
        String code = "P";
        if (!shopService.verifyIfValidShop(req.getProduct().getShopId()).getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return shopService.verifyIfValidShop(req.getProduct().getShopId());

        response = shopService.verifyIfValidShop(req.getProduct().getShopId());
        if (!response.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return response;

        Shop shop = shopService.getShop(req.getProduct().getShopId());

        //validate product code
        if (null != req.getProduct().getCode() && !checkExistingProductCode(req.getProduct().getCode(), shop.getId()).getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkExistingProductCode(req.getProduct().getCode(), shop.getId());

        Users user = userDetailsService.getAuthicatedUser();
        Category category;
        if (req.getProduct().getCategoryId() == 0) {
            category = createCategory(req.getProduct().getCategoryName(), shop);
        } else {
            category = categoryRepository.findTopByIdAndShopId(req.getProduct().getCategoryId(), shop.getId());
        }
        if (null == category)
            return new ResponseModel("01", "Sorry! Invalid product category.");

        Suppliers supplier = null;
        if (null != req.getSupplier() && req.getSupplier().getSuppierId() == 0) {

            if (req.getSupplier().getSupplierName().isEmpty() || null == req.getSupplier().getSupplierName())
                return new ResponseModel("01", "For new supplier name is required");

            supplier = createSupplier(req.getSupplier().getSupplierName(), shop, user);
        } else if (null != req.getSupplier() && req.getSupplier().getSuppierId() > 0) {
            supplier = supplierRepository.findFirstByIdAndShopId(req.getSupplier().getSuppierId(), shop.getId());
        }

        BigDecimal bp = BigDecimal.ZERO;
        BigDecimal sp = BigDecimal.ZERO;
        BigDecimal minSp = BigDecimal.ZERO;

        if (null != req.getStock()) {
            if (null == supplier) {
                return new ResponseModel("01", "Failed! Supplier is required.");
            }


            if (null != req.getStock().getBuyingPrice() && null != req.getStock().getSellingPrice() && null != req.getStock().getMinSellingPrice()) {
                bp = req.getStock().getBuyingPrice();
                sp = req.getStock().getSellingPrice();
                minSp = req.getStock().getMinSellingPrice();

                response = ProductComponent.validateProductPrices(bp, sp, minSp);
                if (!response.getStatus().equals(AppConstants.SUCCESS_STATUS_CODE))
                    return response;
            }

        }

        Product product = new Product();
        product
                .setName(req.getProduct().getName().toUpperCase())
                .setCode(code)
                .setCategoryId(category.getId())
                .setBuyingPrice(bp)
                .setDescription(req.getProduct().getDescription())
                .setStock(0)
                .setSellingPrice(sp)
                .setMinSellingPrice(minSp)
                .setReOrderLevel(req.getProduct().getReOrderLevel())
                .setShopId(shop.getId())
                .setCreatedOn(new Date())
                .setUpdatedOn(new Date());
        product = productRepository.save(product);

        if (null != req.getProduct().getCode() && !req.getProduct().getCode().isEmpty()) {
            product.setCode(req.getProduct().getCode());
        } else {
            product.setCode(product.getCode() + product.getId());
        }

        product = productRepository.save(product);
        if (null != req.getStock()) {
            return addProductStock(product, supplier, shop, req.getStock().getCurrentStock(), bp, sp, minSp, null, user);
        }
        response.setStatus("00");
        response.setMessage("Successfully added product.");
        return response;
    }

    public Category createCategory(String name, Shop shop) {
        Category category = categoryRepository.findTopByNameAndShopId(name, shop.getId());
        if (null == category) {
            category = new Category();
            category.setShopId(shop.getId());
            category.setName(name);
            category.setDescription(name);
            category = categoryRepository.save(category);
        }
        return category;
    }

    public Suppliers createSupplier(String name, Shop shop, Users user) {
        Suppliers supplier = supplierRepository.findFirstByNameAndShopId(name, shop.getId());
        if (null == supplier) {
            supplier = new Suppliers();
            supplier.setPhone(name);
            supplier.setShopId(shop.getId());
            supplier.setName(name);
            supplier.setDescription(name);
            supplier.setCreatedBy(user.getId());
            supplier = supplierRepository.save(supplier);
        }
        return supplier;
    }

    public ResponseModel addProductStock(Product product, Suppliers supplier, Shop shop, Integer quantity, BigDecimal bp,
                                         BigDecimal sellingPrice, BigDecimal minSellingPrice, Invoices invoice, Users user) {
        Stock stock = new Stock();
        if (null != invoice) {
            stock.setInvoiceId(invoice.getId());
        }

        ProductComponent.trackProductTrailAdd(product, shop, user, quantity, "Add Stock", "Add Product Stock.");

        stock
                .setBuyingPrice(bp)
                .setLastStock(product.getStock())
                .setQuantity(quantity)
                .setSupplierId(Long.valueOf(supplier.getId()))
                .setProductId(product.getId())
                .setShopId(shop.getId())
                .setTotalCost(bp.multiply(new BigDecimal(quantity)));

        stock.setCreatedBy(user.getId());
        stock.setSellingPrice(sellingPrice);
        stock.setMinSellingPrice(minSellingPrice);
        stock.setCreatedOn(new Date());
        stock.setUpdatedOn(new Date());
        stockRepository.save(stock);
        product.setStock(product.getStock() + quantity);

        this.productRepository.save(product);
        if (null != invoice) {
            invoice.setTotal(invoice.getTotal().add(stock.getTotalCost()));
            invoicesRepository.save(invoice);
        }

        ProductComponent.autoUpdateProductsPrices(product, shop, user, bp, sellingPrice, minSellingPrice);
        return new ResponseModel("00", "Successfully added new product & stock.");

    }

    public ResponseModel checkExistingProductCode(String productCode, Long shopId) {
        Product product = productRepository.findProductByCodeAndShopId(productCode, shopId);
        if (null != product) {
            return new ResponseModel("01", "Product already exist with similar barcode code.");
        }
        return new ResponseModel("00", "Success");
    }

    @Override
    public ResponseModel addProductToCart(String productId) {
        Users user = userDetailsService.getAuthicatedUser();
        if (null == user) {
            return new ResponseModel("01", "Failed! Un authorized.");
        }
        int userId = user.getId();
        Integer quantity = 1;
        Optional<Product> product = this.productRepository.findById(Long.valueOf(productId));
        if (!product.isPresent()) {
            return new ResponseModel("01", "Sorry! Selling product not found.");
        }

        long shopId = Long.parseLong(_request.getSession().getAttribute("__shopId").toString());

        ResponseModel responseModel = shopService.verifyIfValidShop(shopId);
        if (!responseModel.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return responseModel;


        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUserWeb();
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;

        Product validProduct = product.get();
        if (validProduct.getStock().compareTo(quantity) < 0) {
            return new ResponseModel("01", "Sorry! Product is out of stock.");
        }

        BigDecimal minSellingPrice = validProduct.getMinSellingPrice() != null ? validProduct.getMinSellingPrice() : validProduct.getBuyingPrice();

        if (validProduct.getSellingPrice().compareTo(minSellingPrice) < 0) {
            return new ResponseModel("01", "Sorry! Selling price is too low.");
        }

        CheckOut checkOut = null;
        checkOut = this.checkOutRepository.findDistinctTopByCreatedByAndFlagAndShopId(userId, AppConstants.ACTIVE_RECORD, shopId);

        if (null == checkOut) {
            checkOut = new CheckOut();
            checkOut
                    .setCreatedBy(userId)
                    .setFlag("1")
                    .setShopId(shopId);
            checkOut = checkOutRepository.save(checkOut);
        }

        /*check if product already exist to increment quantity*/
        CheckOutList checkOutList = null;
        Optional<CheckOutList> checkOutListValid = this.checkOutListRepository.findAllByCreatedByAndCheckOutIdAndProductIdAndFlag(userId, checkOut.getId(),
                validProduct.getId(), AppConstants.ACTIVE_RECORD);

        /*if exist increment quantity*/
        if (checkOutListValid.isPresent()) {
            checkOutList = checkOutListValid.get();
            checkOutList.setQuantity(checkOutList.getQuantity() + quantity);
            checkOutList.setTotal(validProduct.getSellingPrice().multiply(BigDecimal.valueOf(checkOutList.getQuantity())));
            checkOutListRepository.save(checkOutList);

            validProduct.setStock(validProduct.getStock() - quantity);
            productRepository.save(validProduct);

        } else {
            checkOutList = new CheckOutList();
            checkOutList
                    .setCheckOutId(checkOut.getId())
                    .setCreatedBy(userId)
                    .setQuantity(quantity)
                    .setFlag("1")
                    .setProductId(product.get().getId())
                    .setSellingPrice(product.get().getSellingPrice())
                    .setTotal(validProduct.getSellingPrice().multiply(BigDecimal.valueOf(quantity)))
                    .setShopId(shopId)
                            .setCreatedOn(new Date())
                            .setUpdatedOn(new Date());
            checkOutListRepository.save(checkOutList);
            /*update stock*/
            validProduct.setStock(validProduct.getStock() - quantity);
            productRepository.save(validProduct);

        }

        return new ResponseModel("00", "Product added to cart.");
    }

    @Override
    public ResponseModel addProductToCartBarcode(String code) {
        Users user = userDetailsService.getAuthicatedUser();
        if (null == user) {
            return new ResponseModel("01", "Failed! Un authorized.");
        }
        int userId = user.getId();
        Integer quantity = 1;

        long shopId = Long.parseLong(_request.getSession().getAttribute("__shopId").toString());

        Product product = this.productRepository.findProductByCodeAndShopId(code, shopId);
        if (null == product) {
            return new ResponseModel("01", "Sorry! Product not found.");
        }


        ResponseModel responseModel = shopService.verifyIfValidShop(shopId);
        if (!responseModel.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return responseModel;


        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUserWeb();
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;


        if (product.getStock().compareTo(quantity) < 0)
            return new ResponseModel("01", "Sorry! Product is out of stock.");


        BigDecimal minSellingPrice = product.getMinSellingPrice() != null ? product.getMinSellingPrice() : product.getBuyingPrice();

        if (product.getSellingPrice().compareTo(minSellingPrice) < 0) {
            return new ResponseModel("01", "Sorry! Selling price is too low.");
        }

        CheckOut checkOut = null;
        checkOut = this.checkOutRepository.findDistinctTopByCreatedByAndFlagAndShopId(userId, AppConstants.ACTIVE_RECORD, shopId);

        if (null == checkOut) {
            checkOut = new CheckOut();
            checkOut
                    .setCreatedBy(userId)
                    .setFlag("1")
                    .setShopId(shopId);
            checkOut = checkOutRepository.save(checkOut);
        }

        /*check if product already exist to increment quantity*/
        CheckOutList checkOutList = null;
        Optional<CheckOutList> checkOutListValid = this.checkOutListRepository.findAllByCreatedByAndCheckOutIdAndProductIdAndFlag(userId, checkOut.getId(),
                product.getId(), AppConstants.ACTIVE_RECORD);

        /*if exist increment quantity*/
        if (checkOutListValid.isPresent()) {
            checkOutList = checkOutListValid.get();
            checkOutList.setQuantity(checkOutList.getQuantity() + quantity);
            checkOutList.setTotal(product.getSellingPrice().multiply(BigDecimal.valueOf(checkOutList.getQuantity())));
            checkOutListRepository.save(checkOutList);

            product.setStock(product.getStock() - quantity);
            productRepository.save(product);

        } else {
            checkOutList = new CheckOutList();
            checkOutList
                    .setCheckOutId(checkOut.getId())
                    .setCreatedBy(userId)
                    .setQuantity(quantity)
                    .setFlag("1")
                    .setProductId(product.getId())
                    .setSellingPrice(product.getSellingPrice())
                    .setTotal(product.getSellingPrice().multiply(BigDecimal.valueOf(quantity)))
                    .setShopId(shopId)
                            .setCreatedOn(new Date())
                                    .setUpdatedOn(new Date());
            checkOutListRepository.save(checkOutList);
            //update product stock
            product.setStock(product.getStock() - quantity);
            productRepository.save(product);

        }

        return new ResponseModel("00", "Product added to cart.");
    }

    @Override
    public ResponseModel addProductToCartMore(AddToCartReq req) {
        ResponseModel responseModel = new ResponseModel();
        responseModel.setStatus("01");

        Users user = userDetailsService.getAuthicatedUser();
        if (null == user) {
            responseModel.setMessage("Invalid user");
            return responseModel;
        }
        int userId = user.getId();
        Integer quantity = req.getQuantity();
        Optional<Product> product = this.productRepository.findById(req.getProductId());
        if (!product.isPresent()) {
            responseModel.setMessage("Sorry invalid product");
            return responseModel;
        }


        Product validProduct = product.get();
        if (validProduct.getStock().compareTo(quantity) < 0) {
            responseModel.setMessage("Failed, Product Out of stock.");
            return responseModel;
        }
        long shopId = Long.parseLong(_request.getSession().getAttribute("__shopId").toString());

        ResponseModel responseModel1 = shopService.verifyIfValidShop(shopId);
        if (!responseModel1.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return responseModel1;

        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUserWeb();
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;
        BigDecimal sellingPrice = req.getSp();

        BigDecimal minSellingPrice = validProduct.getMinSellingPrice() != null ? validProduct.getMinSellingPrice() : validProduct.getBuyingPrice();

        if (!appSettingService.shouldSellBelowPrices(shopId))
            if (sellingPrice.compareTo(minSellingPrice) < 0) {
                responseModel.setMessage("Failed , Selling price is too low.");
                return responseModel;
            }
        CheckOut checkOut = null;
        checkOut = this.checkOutRepository.findDistinctTopByCreatedByAndFlagAndShopId(userId, AppConstants.ACTIVE_RECORD, shopId);
        if (null == checkOut) {
            checkOut = new CheckOut();
            checkOut
                    .setCreatedBy(userId)
                    .setFlag("1")
                    .setShopId(shopId)
                    .setCreatedOn(new Date())
                    .setUpdatedOn(new Date());
            checkOut = checkOutRepository.save(checkOut);
        }
        //check if product already exist to increment quantity
        CheckOutList checkOutList = null;
        Optional<CheckOutList> checkOutListValid = this.checkOutListRepository.findAllByCreatedByAndCheckOutIdAndProductIdAndFlag(userId, checkOut.getId(),
                validProduct.getId(), AppConstants.ACTIVE_RECORD);
        //if exist increment quantity
        if (checkOutListValid.isPresent()) {
            checkOutList = checkOutListValid.get();
            checkOutList.setQuantity(checkOutList.getQuantity() + quantity);
            checkOutList.setTotal(validProduct.getSellingPrice().multiply(BigDecimal.valueOf(checkOutList.getQuantity())));
            checkOutListRepository.save(checkOutList);
            responseModel.setStatus("00");
            responseModel.setMessage("Successfully, added product cart");
        } else {
            checkOutList = new CheckOutList();
            checkOutList
                    .setCheckOutId(checkOut.getId())
                    .setCreatedBy(userId)
                    .setQuantity(quantity)
                    .setFlag(AppConstants.ACTIVE_RECORD)
                    .setProductId(product.get().getId())
                    //.setSellingPrice(validProduct.getSellingPrice())
                    .setSellingPrice(sellingPrice)
                    .setTotal(sellingPrice.multiply(BigDecimal.valueOf(quantity)))
                    .setShopId(shopId);
            checkOutListRepository.save(checkOutList);
            responseModel.setStatus("00");
            responseModel.setMessage("Successfully, added product cart");
        }

        //update stock
        validProduct.setStock(validProduct.getStock() - quantity);
        productRepository.save(validProduct);

        return responseModel;
    }

    @Override
    public ResponseModel removeProductFromCart(String checkOuListId) {
        Optional<CheckOutList> checkOutList = this.checkOutListRepository.findById(Long.valueOf(checkOuListId));

        if (!checkOutList.isPresent()) {
            return new ResponseModel("01", "Sorry! Product not found in the cart.");
        }
        CheckOutList validCheckOutList = checkOutList.get();
        Product validProduct = this.productRepository.findById(validCheckOutList.getProductId()).get();

        /*update stock*/
        validProduct.setStock(validProduct.getStock() + validCheckOutList.getQuantity());
        productRepository.save(validProduct);

        checkOutListRepository.delete(validCheckOutList);

        return new ResponseModel("00", "Removed successfully");
    }

    public ResponseModel editCartQuantity(String checkOuListId, Integer quantity) {
        Optional<CheckOutList> checkOutList = this.checkOutListRepository.findById(Long.valueOf(checkOuListId));

        if (!checkOutList.isPresent()) {
            return new ResponseModel("01", "Sorry! Product not found in the cart.");
        } else if (quantity <= 0) {
            return new ResponseModel("01", "Sorry! Quantity must be one or more items.");
        }
        CheckOutList editCheckOutList = checkOutList.get();
        Product product = this.productRepository.findById(editCheckOutList.getProductId()).get();
        Integer stock = product.getStock() + editCheckOutList.getQuantity();

        if (stock < quantity) {
            return new ResponseModel("01", "Product stock is below that quantity sold.");
        }
        product.setStock(product.getStock() + editCheckOutList.getQuantity());
        productRepository.save(product);

        editCheckOutList.setQuantity(quantity);
        editCheckOutList.setTotal(editCheckOutList.getSellingPrice().multiply(new BigDecimal(quantity)));
        checkOutListRepository.save(editCheckOutList);

        product.setStock(product.getStock() - editCheckOutList.getQuantity());
        this.productRepository.save(product);

        return new ResponseModel("00", "Removed successfully");
    }

    @Override
    public ResponseModel completeSell(WebCompleteSellReq req) {
        ResponseModel response = new ResponseModel();
        response.setStatus("01");

        boolean checkIfsCustomerAvailable = isCustomerAvailable(null, req.getPhone());

        long shopId = Long.parseLong(_request.getSession().getAttribute("__shopId").toString());

        ResponseModel responseModel = shopService.verifyIfValidShop(shopId);
        if (!responseModel.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return responseModel;
        Users user = userDetailsService.getAuthicatedUser();
        if (null == user) {
            response.setMessage("Un Authorized");
            return response;
        }

        Shop shop = shopService.getShop(shopId);

        ResponseModel checkIfExceed = SystemComponent.checkIfShopExceededNumberOfEmployees(shop, user);
        if (!checkIfExceed.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkIfExceed;

        if (null == req.getMPESA() && null == req.getCASH() && null == req.getCREDIT()) {
            return new ResponseModel("01", "Sorry!  Select payment method used.");
        }
        BigDecimal cashAmountGiven = BigDecimal.ZERO;
        BigDecimal mpesaAmount = BigDecimal.ZERO;
        BigDecimal cashChange = BigDecimal.ZERO;
        BigDecimal cashAmount = BigDecimal.ZERO;
        BigDecimal creditAmount = BigDecimal.ZERO;

        PaymentMethods cashPaymentMode = null;
        Customer customer = null;
        if (null != req.getCASH()) {
            cashPaymentMode = paymentMethodsRepository.findTopByCode(PaymentMethods.CASH);
            ShopPaymentMethods shopPaymentMethod = shopPaymentMethodsRepository.findFirstByShopIdAndPaymentMethodId(shopId, cashPaymentMode.getId());

            if (null == shopPaymentMethod)
                return new ResponseModel("01", "Cash payment mode not supported.");

            if (null == req.getCashAmount())
                return new ResponseModel("01", "Cash Given is required.");

            cashAmountGiven = req.getCashAmount();
        }

        PaymentMethods mpesaPaymentMode = null;
        if (null != req.getMPESA()) {
            mpesaPaymentMode = paymentMethodsRepository.findTopByCode(PaymentMethods.MPESA);
            ShopPaymentMethods shopPaymentMethod = shopPaymentMethodsRepository.findFirstByShopIdAndPaymentMethodId(shopId, mpesaPaymentMode.getId());

            if (null == shopPaymentMethod)
                return new ResponseModel("01", "Mpesa payment mode not supported.");

            if (null == req.getMpesaAmount())
                return new ResponseModel("01", "Amount for selected mpesa payment method is required.");

            mpesaAmount = req.getMpesaAmount();
        }

        PaymentMethods creditPaymentMode = null;
        if (null != req.getCREDIT()) {
            creditPaymentMode = paymentMethodsRepository.findTopByCode(PaymentMethods.CREDIT);
            ShopPaymentMethods shopPaymentMethod = shopPaymentMethodsRepository.findFirstByShopIdAndPaymentMethodId(shopId, creditPaymentMode.getId());

            if (null == shopPaymentMethod)
                return new ResponseModel("01", "Credit payment mode not supported.");

            if (null == req.getCREDIT())
                return new ResponseModel("01", "Amount for selected credit payment method is required.");

            creditAmount = req.getCreditAmount();
        }

        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUserWeb();
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;
        if (null != mpesaPaymentMode) {
            if (mpesaPaymentMode.getCode().equalsIgnoreCase(PaymentMethods.MPESA) && (null == req.getTransRef() || req.getTransRef().isEmpty())) {
                response.setMessage("Sorry mpesa ref is required");
                return response;
            }
        }

        if (null != creditPaymentMode) {
            if (null == req.getPhone() || req.getPhone().isEmpty()) {
              /*  response.setMessage("Sorry customer info is required for credit payment method.");
                return response;*/

                //get default customer or create unknown
                customer = customerRepository.findDistinctByPhoneAndShopId(Customer.CUSTOMER_DEFUALT_PHONE, shopId);
                if (null == customer)
                    customer = createDefaultCustomer(user, shop);
            } else {
                if (!appFunctions.validatePhoneNumber(req.getPhone()))
                    return new ResponseModel("01", "Sorry! Customer phone number is invalid");

                req.setPhone(appFunctions.getInternationalPhoneNumber(req.getPhone(), ""));

                customer = customerRepository.findFirstByPhoneAndShopId(req.getPhone(), shopId);

                if (null == customer)
                    return new ResponseModel("01", "Sorry! Customer info not found.");
            }
        }

        int userId = user.getId();
        CheckOut checkOut = null;
        checkOut = this.checkOutRepository.findDistinctTopByCreatedByAndFlagAndShopId(userId, AppConstants.ACTIVE_RECORD, shopId);

        String saleRef = RRNGenerator.getInstance(AppConstants.SALES_PREPAENDER).getRandomCharacterRRN();

        SalesTransactions salesTransactions = new SalesTransactions();

        String phone;
        if (null != customer) {
            salesTransactions.setCustomerId(customer.getId());
        } else if (null != req.getPhone() && !req.getPhone().isEmpty()) {
            phone = appFunctions.getInternationalPhoneNumber(req.getPhone(), "");
            customer = customerRepository.findDistinctByPhoneAndShopId(phone, shopId);
            if (null != customer)
                salesTransactions.setCustomerId(customer.getId());
        }
        if (null == checkOut)
            return new ResponseModel("01", "Sorry! no products in the cart.");

        BigDecimal totalAmount = checkOutListRepository.getSumTopPay(userId, checkOut.getId(), AppConstants.ACTIVE_RECORD);
        totalAmount = totalAmount.setScale(0, BigDecimal.ROUND_UP);
        if (totalAmount.compareTo(cashAmountGiven.add(mpesaAmount).add(creditAmount)) > 0)
            return new ResponseModel("01", "Amounts are below total cost of products.");

        if (null != mpesaPaymentMode && null != cashPaymentMode) {
            cashAmount = cashAmountGiven;
            if (totalAmount.compareTo(mpesaAmount.add(cashAmountGiven).add(creditAmount)) < 0)
                return new ResponseModel("01", "Amount provided for splitted bill are too high.");
        }

        if (null != mpesaPaymentMode && req.getMpesaAmount().compareTo(totalAmount) > 0)
            return new ResponseModel("01", "Sorry amount paid via mpesa more than required.");

        if (null == mpesaPaymentMode && null != cashPaymentMode) {
            cashChange = cashAmountGiven.subtract(totalAmount);
            cashAmount = totalAmount;
        }

        List<CheckOutList> checkOutLists = this.checkOutListRepository.findAllByCreatedByAndCheckOutIdAndFlag(userId, checkOut.getId(), AppConstants.ACTIVE_RECORD);
        if (checkOutLists.size() == 0) {
            response.setMessage("Sorry! no products in the cart.");
            return response;
        }
        checkOut.setFlag(AppConstants.CART_CLEARED);
        checkOutRepository.save(checkOut);

        salesTransactions.setReferenceNo(saleRef);
        salesTransactions.setShopId(shopId);
        salesTransactions.setCreatedBy(user.getId());
        salesTransactions.setStatus(AppConstants.ACTIVE_RECORD);
        salesTransactions.setTotalAmount(totalAmount);
        salesTransactionsRepository.save(salesTransactions);

        SalesTransactionPayments cashTransactionPayments = new SalesTransactionPayments();
        SalesTransactionPayments mpesaTransactionPayment = new SalesTransactionPayments();
        SalesTransactionPayments creditTransactionPayment = new SalesTransactionPayments();
        if (null != mpesaPaymentMode) {
            mpesaTransactionPayment.setPaymentReferenceNo(req.getTransRef());
            mpesaTransactionPayment.setAmount(req.getMpesaAmount());
            mpesaTransactionPayment.setCreatedBy(user.getId());
            mpesaTransactionPayment.setFlag(AppConstants.ACTIVE_RECORD);
            mpesaTransactionPayment.setSalesTransactionId(salesTransactions.getId());
            mpesaTransactionPayment.setPaymentMethod(mpesaPaymentMode.getId());
            mpesaTransactionPayment.setCreatedOn(new Date());
            mpesaTransactionPayment.setShopId(shopId);
            salesTransactionPaymentsRepository.save(mpesaTransactionPayment);
        }
        if (null != cashPaymentMode) {
            cashTransactionPayments.setAmount(cashAmount);
            cashTransactionPayments.setCreatedBy(user.getId());
            cashTransactionPayments.setFlag(AppConstants.ACTIVE_RECORD);
            cashTransactionPayments.setSalesTransactionId(salesTransactions.getId());
            cashTransactionPayments.setPaymentMethod(cashPaymentMode.getId());
            cashTransactionPayments.setCreatedOn(new Date());
            cashTransactionPayments.setShopId(shopId);
            cashTransactionPayments.setCashGiven(cashAmountGiven);
            cashTransactionPayments.setCashChange(cashChange);
            salesTransactionPaymentsRepository.save(cashTransactionPayments);
        }
        if (null != creditPaymentMode) {
            creditTransactionPayment.setAmount(creditAmount);
            creditTransactionPayment.setCreatedBy(user.getId());
            creditTransactionPayment.setFlag(AppConstants.ACTIVE_RECORD);
            creditTransactionPayment.setSalesTransactionId(salesTransactions.getId());
            creditTransactionPayment.setPaymentMethod(creditPaymentMode.getId());
            creditTransactionPayment.setCreatedOn(new Date());
            creditTransactionPayment.setShopId(shopId);
            creditTransactionPayment.setCashGiven(BigDecimal.ZERO);
            creditTransactionPayment.setCashChange(BigDecimal.ZERO);
            salesTransactionPaymentsRepository.save(creditTransactionPayment);

            //do credit record
            doCredit(user, salesTransactions, customer, shop, creditAmount);
        }

        //do accounting to  sales
        doAddAccountingAndProfit(shop, user, salesTransactions, checkOutLists);

            /*if (checkIfsCustomerAvailable) {
                //save customer
                Customer customer = customerRepository.findDistinctByPhoneAndShopId(phone, shopId);
                if (null == customer) {
                    customer = new Customer();
                    customer
                            .setName(name)
                            .setPhone(phone)
                            .setShopId(shopId);
                    customerRepository.save(customer);
                }
                checkOut.setCustomerId(customer.getId());
            }*/

        clearUserCart(checkOut, checkOutLists);
        Map<String, Object> map = new HashMap<>();
        map.put("saleId", salesTransactions.getId());
        response.setStatus("00");
        response.setMessage("Transaction completed. Do you want to print receipt?");

        response.setData(map);

        return response;
    }

    public Customer createDefaultCustomer(Users user, Shop shop) {
        Customer customer = new Customer();
        customer
                .setName(Customer.CUSTOMER_DEFUALT_NAME)
                .setPhone(Customer.CUSTOMER_DEFUALT_PHONE)
                .setDescription(Customer.CUSTOMER_DEFUALT_NAME)
                .setCreatedOn(new Date())
                .setUpdatedOn(new Date())
                .setShopId(shop.getId())
                .setCreatedBy(user.getId());
        customer = customerRepository.save(customer);
        return customer;
    }

    private boolean isCustomerAvailable(String name, String phone) {
        if ((null != name) && (null != phone) && (!phone.isEmpty()) && (!name.isEmpty())) {
            return true;
        } else {
            return false;
        }
    }

    public void clearUserCart(CheckOut checkOut, List<CheckOutList> checkOutList) {

        checkOutListRepository.deleteAll(checkOutList);
        checkOutRepository.delete(checkOut);
    }

    @Override
    public ResponseModel editProduct(String productId, String name, String bp, String sp, String minSp, String reOrderlevel, String description, String category, String code) {
        ResponseModel response = new ResponseModel();
        response.setStatus("01");
        BigDecimal bp1 = new BigDecimal(bp);
        BigDecimal sp1 = new BigDecimal(sp);
        BigDecimal minSp1 = new BigDecimal(minSp);
        Integer reOrdeLevel1 = Integer.parseInt(reOrderlevel);

        Users users = userDetailsService.getAuthicatedUser();
        UserRole userRole = userRoleRepository.findDistinctTopByUserId(users.getId());
        Optional<Product> product = this.productRepository.findById(Long.valueOf(productId));
        if (!product.isPresent()) {
            response.setStatus("01");
            response.setMessage("Sorry Invalid product.");
            return response;
        }
        Product validProduct = product.get();
        Shop shop = shopService.getShop(validProduct.getShopId());

        ResponseModel responseModel = shopService.verifyIfValidShop(shop.getId());
        if (!responseModel.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return responseModel;
        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUserWeb();
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;
        response = ProductComponent.validateProductPrices(bp1, sp1, minSp1);

        if (!response.getStatus().equals(AppConstants.SUCCESS_STATUS_CODE))
            return response;

        Product checkIfCodeExists = productRepository.findProductByCodeAndShopId(code, shop.getId());
        if (null != checkIfCodeExists && !checkIfCodeExists.getId().equals(validProduct.getId()))
            return new ResponseModel("01", "Sorry products already exists with product with simar barcode.");
        ProductComponent.trackPriceProductPriceChange(validProduct, shop, users, bp1, sp1, minSp1, "Edit", "Prices edited");
        validProduct
                .setName(name.toUpperCase())
                .setBuyingPrice(bp1)
                .setSellingPrice(sp1)
                .setMinSellingPrice(minSp1)
                .setReOrderLevel(reOrdeLevel1)
                .setDescription(description)
                .setCategoryId(Integer.valueOf(category))
                .setCode(code);

        productRepository.save(validProduct);
        response.setStatus("00");
        response.setMessage("Successfully edited product.");
        return response;
    }

    @Override
    public ResponseModel addCategory(String name, String description, @NotNull Long shopId) {
        ResponseModel responseModel = new ResponseModel();
        responseModel.setStatus("01");
        Users users = userDetailsService.getAuthicatedUser();

        Category checkExists = categoryRepository.findTopByNameAndShopId(name, shopId);
        if (null != checkExists) {
            responseModel.setMessage("Sorry similar category exists for this shop.");
            return responseModel;
        }
        Shop shop = shopService.getShop(shopId);
        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUser(shop, users);
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;

        Category category = new Category();
        category
                .setDescription(description)
                .setName(name)
                .setShopId(shopId);
        categoryRepository.save(category);
        responseModel.setStatus("00");
        responseModel.setMessage("Successfully created category.");

        return responseModel;
    }

    private void doAddAccountingAndProfit(Shop shop, Users user, SalesTransactions salesTransaction, List<CheckOutList> checkOutLists) {

        for (CheckOutList checkOutList : checkOutLists) {

            Product product = checkOutList.getProductLink();

            BigDecimal profitPerProduct = checkOutList.getSellingPrice().subtract(product.getBuyingPrice());
            BigDecimal totalProfit = profitPerProduct.multiply(new BigDecimal(checkOutList.getQuantity()));
            BigDecimal total = checkOutList.getSellingPrice().multiply(new BigDecimal(checkOutList.getQuantity()));

            Sales sales = new Sales();
            sales
                    .setCreatedBy(user.getId())
                    .setFlag(AppConstants.ACTIVE_RECORD)
                    // .setPaymentMode(salesTransaction.getPaymentMode())
                    .setSellingPrice(product.getSellingPrice())
                    .setProductId(checkOutList.getProductId())
                    .setQuantity(checkOutList.getQuantity())
                    .setTotalAmount(total)
                    .setProfitPerProduct(profitPerProduct)
                    .setTotalProfit(totalProfit)
                    .setShopId(shop.getId())
                    .setSalesTransactionId(salesTransaction.getId());

            sales.setBuyingPrice(product.getBuyingPrice());
            sales.setMinSellingPrice(product.getMinSellingPrice());
            sales.setSoldAt(checkOutList.getSellingPrice());
            sales.setCreatedOn(new Date());
            sales.setUpdatedOn(new Date());
            salesRepository.save(sales);


            ProductComponent.trackProductTrailDeductWeb(product, shop, user, checkOutList.getQuantity(), "sold", "Sold Product Via Web.");

        }
    }

    @Override
    public Map<String, Object> deleteProduct(String productId, String password) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "01");
        Long id = Long.valueOf(productId);
        Optional<Product> checkProduct = productRepository.findById(id);

        if (!checkProduct.isPresent()) {
            response.put("message", "Sorry failed please try again later");
            logger.info("editing product failed invalid product ");
            return response;
        }

        Product product = checkProduct.get();

        Users user = userDetailsService.getAuthicatedUser();
        if (null == user) {
            response.put("message", "Invalid user");
            return response;
        }
        //verify user password
        if (!verifyPassword(password, user)) {
            response.put("message", "Sorry! Invalid password.");
            return response;
        }

        product.setFlag(AppConstants.SOFT_DELETED);
        productRepository.save(product);

        response.put("status", "00");
        response.put("message", "Successfully deleted " + product.getName());

        return response;
    }

    @Override
    public Map<String, Object> deleteTransaction(String transactionId, String password) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "01");
        Long id = Long.valueOf(transactionId);
        Optional<Sales> checkSell = salesRepository.findById(id);

        if (!checkSell.isPresent()) {
            response.put("message", "Sorry failed please try again later");
            logger.info("deleting sale  failed invalid sale ");
            return response;
        }

        Sales sale = checkSell.get();

        Users user = userDetailsService.getAuthicatedUser();
        if (null == user) {
            response.put("message", "Invalid user");
            return response;
        }
        //verify user password
        if (!verifyPassword(password, user)) {
            response.put("message", "Sorry! Invalid password.");
            return response;
        }
        Product product = sale.getProductLink();
        salesRepository.delete(sale);

        product.setStock(product.getStock() + sale.getQuantity());

        productRepository.save(product);

        response.put("status", "00");
        response.put("message", "Successfully deleted sale");
        return response;
    }

    public Boolean verifyPassword(String password, Users user) {
        boolean status = false;

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (passwordEncoder.matches(password, user.getPassword())) {
            status = true;
        }

        return status;

    }


    public ResponseModel apiSell(ApiSellRequest request, Shop shop, Users user, SalesTransactionPayments cashSalesTransactionPayments,
                                 SalesTransactionPayments mpesaSalesTransactionPayments, SalesTransactionPayments creditSalesTransactionPayments, Customer customer) {

        SalesTransactions salesTransactions = new SalesTransactions();
        String phone;

        if (null != customer)
            salesTransactions.setCustomerId(customer.getId());

        String saleRef = RRNGenerator.getInstance(AppConstants.SALES_PREPAENDER).getRandomCharacterRRN();
        salesTransactions.setReferenceNo(saleRef);
        salesTransactions.setShopId(shop.getId());
        salesTransactions.setCreatedBy(user.getId());
        // salesTransactions.setPaymentMode(paymentMethods.getId());
        salesTransactions.setStatus(AppConstants.ACTIVE_RECORD);
        salesTransactions.setTotalAmount(request.getTotalAmount());
        salesTransactionsRepository.save(salesTransactions);

        if (null != cashSalesTransactionPayments) {
            cashSalesTransactionPayments.setSalesTransactionId(salesTransactions.getId());
            cashSalesTransactionPayments.setShopId(shop.getId());
            cashSalesTransactionPayments.setCreatedBy(user.getId());
            cashSalesTransactionPayments.setFlag(AppConstants.ACTIVE_RECORD);
            cashSalesTransactionPayments.setCreatedOn(new Date());
            salesTransactionPaymentsRepository.save(cashSalesTransactionPayments);
        }

        if (null != mpesaSalesTransactionPayments) {
            mpesaSalesTransactionPayments.setSalesTransactionId(salesTransactions.getId());
            mpesaSalesTransactionPayments.setShopId(shop.getId());
            mpesaSalesTransactionPayments.setCreatedBy(user.getId());
            mpesaSalesTransactionPayments.setCreatedOn(new Date());
            mpesaSalesTransactionPayments.setFlag(AppConstants.ACTIVE_RECORD);
            salesTransactionPaymentsRepository.save(mpesaSalesTransactionPayments);
        }

        if (null != creditSalesTransactionPayments) {
            creditSalesTransactionPayments.setShopId(shop.getId());
            creditSalesTransactionPayments.setSalesTransactionId(salesTransactions.getId());
            creditSalesTransactionPayments.setCreatedBy(user.getId());
            creditSalesTransactionPayments.setCreatedOn(new Date());
            creditSalesTransactionPayments.setFlag(AppConstants.ACTIVE_RECORD);
            salesTransactionPaymentsRepository.save(creditSalesTransactionPayments);

            //do credit record
            doCredit(user, salesTransactions, customer, shop, creditSalesTransactionPayments.getAmount());
        }
        //do accounting to  sales
        apiSellAccountingAndProfit(shop, user, salesTransactions, request);
        return new ResponseModel("00", "Transaction completed successfully.");
    }

    private void apiSellAccountingAndProfit(Shop shop, Users user, SalesTransactions salesTransaction, ApiSellRequest request) {

        List<Sales> sales = new ArrayList<>();
        for (ApiSellRequest.SoldItems item : request.getSoldItems()) {
            Product product = productRepository.findById(item.getProductId()).get();

            BigDecimal profitPerProduct = item.getSellingPrice().subtract(product.getBuyingPrice());
            BigDecimal totalProfit = profitPerProduct.multiply(new BigDecimal(item.getQuantity()));
            BigDecimal total = item.getSellingPrice().multiply(new BigDecimal(item.getQuantity()));

            Sales sale = new Sales();
            sale
                    .setCreatedBy(user.getId())
                    .setFlag(AppConstants.ACTIVE_RECORD)
                    //.setPaymentMode(salesTransaction.getPaymentMode())
                    .setSellingPrice(product.getSellingPrice())
                    .setProductId(item.getProductId())
                    .setQuantity(item.getQuantity())
                    .setTotalAmount(total)
                    .setProfitPerProduct(profitPerProduct)
                    .setTotalProfit(totalProfit)
                    .setShopId(shop.getId())
                    .setSalesTransactionId(salesTransaction.getId());

            sale.setBuyingPrice(product.getBuyingPrice());
            sale.setMinSellingPrice(product.getMinSellingPrice());
            sale.setSoldAt(item.getSellingPrice());

            sales.add(sale);

            ProductComponent.trackProductTrailDeduct(product, shop, user, item.getQuantity(), "sold", "Sold Product Mobile App.");

            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);
        }
        salesRepository.saveAll(sales);
    }

    public Invoices checkIfInvoiceExistsAndCreate(String invoiceNo, String invoiceDate, String invoiceStatus, Shop shop, Suppliers supplier, Users user) {
        Invoices invoice = invoicesRepository.findTopByShopIdAndSupplierIdAndInvoiceNoAndInvoiceDate(shop.getId(), Long.valueOf(supplier.getId()), invoiceNo, invoiceDate);
        if (null == invoice) {
            invoice = new Invoices();
            invoice.setCreatedBy((long) user.getId());
            invoice.setShopId(shop.getId());
            invoice.setSupplierId((long) supplier.getId());
            invoice.setInvoiceNo(invoiceNo);
            invoice.setStatus(invoiceStatus.toUpperCase());
            invoice.setInvoiceDate(appFunctions.formatStrDateToSystemDate(invoiceDate));
            invoice.setCreatedOn(new Date());
            invoice.setTotal(BigDecimal.ZERO);
            invoicesRepository.save(invoice);
        }
        return invoice;
    }

    public void doCredit(Users user, SalesTransactions salesTransaction, Customer customer, Shop shop, BigDecimal amount) {
        Credits credit = new Credits();
        credit.setAmount(amount);
        credit.setBalance(amount);
        credit.setShopId(shop.getId());
        credit.setCustomerId(customer.getId());
        credit.setCreatedBy(user.getId());
        credit.setSaleTransactionId(salesTransaction.getId());
        credit.setPaymentStatus(AppConstants.CREDIT_PENDING_PAYMNET);
        credit.setFlag(AppConstants.ACTIVE_RECORD);
        credit.setCreatedOn(new Date());

        creditsRepository.save(credit);
    }

    public ResponseModel payDebt(HttpServletRequest request) {
        Long id = Long.valueOf(request.getParameter("id"));
        BigDecimal amount = new BigDecimal(request.getParameter("amount"));

        return processPayDebt(id, amount);
    }

    public ResponseModel processPayDebt(Long id, BigDecimal amount) {
        Optional<Credits> checkCredit = creditsRepository.findById(id);
        if (!checkCredit.isPresent())
            return new ResponseModel("01", "Sorry record not found.");
        Credits credit = checkCredit.get();
        Users user = userDetailsService.getAuthicatedUser();

        if (credit.getBalance().compareTo(amount) < 0)
            return new ResponseModel("01", "Sorry Amount is too high than balance.");

        if (credit.getPaymentStatus().equalsIgnoreCase(AppConstants.CREDIT_PAYMENT_COMPLETED))
            return new ResponseModel("01", "Sorry debt already paid.");

        if (!credit.getFlag().equalsIgnoreCase(AppConstants.ACTIVE_RECORD))
            return new ResponseModel("01", "Sorry! This debt is not available for clearance.");

        ResponseModel response = new ResponseModel();
        response.setStatus("00");
        CreditPayments creditPayments = new CreditPayments();
        creditPayments.setAmount(amount);
        creditPayments.setBalance(credit.getBalance().subtract(amount));
        creditPayments.setCreatedBy(user.getId());
        creditPayments.setCreditId(credit.getId());
        creditPayments.setCustomerId(credit.getId());
        creditPayments.setShopId(credit.getShopId());
        creditPayments.setFlag(AppConstants.ACTIVE_RECORD);
        creditPaymentsRepository.save(creditPayments);

        credit.setBalance(credit.getBalance().subtract(amount));
        if (credit.getBalance().compareTo(BigDecimal.ZERO) == 0) {
            credit.setPaymentStatus(AppConstants.CREDIT_PAYMENT_COMPLETED);
            response.setMessage("Debt has been settled fully.");
        } else {
            credit.setPaymentStatus(AppConstants.CREDIT_PARTIALLY_PAID);
            response.setMessage("Debt was partially paid balance new balance is " + credit.getBalance());
        }
        creditsRepository.save(credit);
        return response;
    }
}
