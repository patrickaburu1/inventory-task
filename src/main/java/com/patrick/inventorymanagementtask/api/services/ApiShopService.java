package com.patrick.inventorymanagementtask.api.services;

import com.patrick.inventorymanagementtask.api.models.*;
import com.patrick.inventorymanagementtask.config.AppSettingService;
import com.patrick.inventorymanagementtask.entities.*;
import com.patrick.inventorymanagementtask.entities.products.Customer;
import com.patrick.inventorymanagementtask.entities.products.PaymentMethods;
import com.patrick.inventorymanagementtask.entities.products.Product;
import com.patrick.inventorymanagementtask.entities.products.Shop;
import com.patrick.inventorymanagementtask.entities.user.*;
import com.patrick.inventorymanagementtask.models.PaymentMethodsAllowedRes;
import com.patrick.inventorymanagementtask.models.RenewPlanReq;
import com.patrick.inventorymanagementtask.properties.ApplicationMessages;
import com.patrick.inventorymanagementtask.properties.ApplicationPropertiesValues;
import com.patrick.inventorymanagementtask.repositories.*;
import com.patrick.inventorymanagementtask.repositories.product.*;
import com.patrick.inventorymanagementtask.repositories.user.*;
import com.patrick.inventorymanagementtask.security.service.CustomUserDetailsService;
import com.patrick.inventorymanagementtask.service.DashboardService;

import com.patrick.inventorymanagementtask.service.ProductService;
import com.patrick.inventorymanagementtask.service.ShopService;
import com.patrick.inventorymanagementtask.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author patrick on 2/11/20
 * @project shop-pos
 */
@Service
@Transactional
public class ApiShopService {

    @Autowired
    private DashboardService dashboardService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShopService shopService;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private ShopEmployeesRepository shopEmployeesRepository;
    @Autowired
    private ShopEmployeesGroupRightsRepository shopEmployeesGroupRightsRepository;
    @Autowired
    private ShopRightsRepository shopRightsRepository;
    @Autowired
    private AuditLogRepository auditLogRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PaymentMethodsRepository paymentMethodsRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private ShopPaymentMethodsRepository shopPaymentMethodsRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AppFunctions appFunctions;
    @Autowired
    private ExpensesRepository expensesRepository;
    @Autowired
    private ShopPaymentPackagesRepository shopPaymentPackagesRepository;
    @Autowired
    private PaymentPlanEntriesRepository paymentPlanEntriesRepository;

    @Autowired
    private ShopPackagePlansRepository shopPackagePlansRepository;
    @Autowired
    private ShopCategoriesRepository shopCategoriesRepository;
    @Autowired
    private ShopEmployeeGroupsRepository shopEmployeeGroupsRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private UserTypeRepository userTypeRepository;

    @Autowired
    private RoleRespository roleRespository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ApplicationPropertiesValues appProperties;
    @Autowired
    private AppSettingService appSettingService;
    @Autowired
    private ShopRepository shopRepository;


    private Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * get shops
     */
    public ResponseModel getShops() {

        Users user = userDetailsService.getAuthicatedUser();
        Map<String, Object> res = shopService.processGetShops(user);
        Map<String, Object> map = new HashMap<>();
        map.put("shops", res.get("data"));

        List<ApiShopCategoriesRes> apiShopCategoriesRes = new ArrayList<>();
        List<ShopCategories> shopCategories = shopCategoriesRepository.findByFlagOrderByCategoryAsc(AppConstants.ACTIVE_RECORD);
        shopCategories.forEach(node -> {
            ApiShopCategoriesRes category = new ApiShopCategoriesRes();
            category.setCategory(node.getCategory());
            category.setId(node.getId());
            apiShopCategoriesRes.add(category);
        });

        map.put("shopCategories", apiShopCategoriesRes);

        return new ResponseModel(res.get("status").toString(), res.get("message").toString(), map);
    }

    /**
     * add shop
     */
    public ResponseModel addShop(AppCreateShopReq request) {
        Users user = userDetailsService.getAuthicatedUser();
        return shopService.apiCreateNewShop(request);
    }

    /**
     * get shops
     */
    public ResponseModel shopsPackages(Long shopId) {
        Users user = userDetailsService.getAuthicatedUser();

        Shop shop = null;
        if (null != shopId)
            shop = shopService.getShop(shopId);
        List<ShopPaymentPackages> shopPaymentPackages = shopPaymentPackagesRepository.findAllByFlagAndTrialOrderByDisplayOrderAsc(AppConstants.ACTIVE_RECORD, false);

        List<ApiShopPaymentPackagesRes> apiShopPaymentPackagesRess = new ArrayList<>();
        Shop finalShop = shop;

        shopPaymentPackages.forEach(node -> {
            ApiShopPaymentPackagesRes apiShopPaymentPackagesRes = new ApiShopPaymentPackagesRes();
            apiShopPaymentPackagesRes.setDescription(node.getDescription());
            apiShopPaymentPackagesRes.setName(node.getName());

            List<ShopPackagePlans> packagePlans = shopPackagePlansRepository.findAllByPackageIdAndFlag(node.getId(), AppConstants.ACTIVE_RECORD);
            List<ApiShopPaymentPackagesRes.ApiShopPlansRes> apiShopPlans = new ArrayList<>();
            packagePlans.forEach(plan -> {
                ApiShopPaymentPackagesRes.ApiShopPlansRes apiShopPlansRes = new ApiShopPaymentPackagesRes.ApiShopPlansRes();

                apiShopPlansRes.setAmount(plan.getAmount());
                apiShopPlansRes.setDuration(plan.getDuration());
                apiShopPlansRes.setId(plan.getId());
                apiShopPlansRes.setPlan(plan.getPlan());

                if (null != finalShop && null != finalShop.getPlanId() && finalShop.getPlanId().equals(plan.getId()))
                    apiShopPlansRes.setCurrentPlan(true);

                apiShopPlans.add(apiShopPlansRes);
            });

            apiShopPaymentPackagesRes.setPlans(apiShopPlans);

          /*  if (null!= finalShop && finalShop.getPlanId().equals(node.getId()))
                apiShopPaymentPackagesRes.setCurrentPlan(true);*/

            apiShopPaymentPackagesRes.setTrial(node.getTrial());
            apiShopPaymentPackagesRess.add(apiShopPaymentPackagesRes);
        });
        return new ResponseModel("00", "success", apiShopPaymentPackagesRess);
    }

    public boolean checkIfAllowed(String privilege, Long shopId) {
        Users usersCheck = userDetailsService.getAuthicatedUser();
        ShopEmployees userRoleCheck = shopEmployeesRepository.findDistinctTopByUserIdAndShopId(usersCheck.getId(), shopId);
        if (null == userRoleCheck) {
            return false;
        }
        ShopEmployeeGroups role = userRoleCheck.getShopEmployeeGroupsLink();

        Optional<ShopRights> checkPrivilege = shopRightsRepository.findFirstByCode(privilege);
        if (!checkPrivilege.isPresent()) {
            return false;
        } else {
            ShopRights shopRights = checkPrivilege.get();
            Optional<ShopEmployeesGroupRights> rolePrivileges = shopEmployeesGroupRightsRepository.findAllByRoleIdAndPrivilegeIdAndShopId(role.getId(), shopRights.getId(), shopId);
            if (!rolePrivileges.isPresent()) {
                AuditLog auditLog = new AuditLog();
                auditLog
                        .setCreatedBy(usersCheck.getId())
                        .setCreatedOn(new Date())
                        .setDescription("Access denied on " + privilege)
                        .setFlag(AppConstants.ACTIVE_RECORD)
                        .setUpdatedOn(new Date());
                auditLogRepository.save(auditLog);

                return false;
            }
        }
        return true;
    }

    public ResponseModel sell(ApiSellRequest request, HttpServletRequest httpServletRequest) {
        Users user = userDetailsService.getAuthicatedUser();

        ResponseModel responseModel = shopService.verifyIfValidShop(request.getShopId());
        if (!responseModel.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return responseModel;

        if (!checkIfAllowed(ShopRights.PRIVILEGE_SELL, request.getShopId()))
            return new ResponseModel("01", "Sorry! You are not allowed to perform this operation.");
        Shop shop = shopService.getShop(request.getShopId());

        ResponseModel checkIfExceedEmployees = SystemComponent.checkIfShopExceededNumberOfEmployees(shop, user);
        if (!checkIfExceedEmployees.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkIfExceedEmployees;

        dashboardService.saveAuditLogMobile("sell", shop.getId(), httpServletRequest);

        BigDecimal total = BigDecimal.ZERO;
        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUser(shop, user);
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;

        if (request.getPaymentMethods().size() == 0)
            return new ResponseModel("01", "Failed! select at-least one payment method.");

        if (request.getSoldItems().size() == 0)
            return new ResponseModel("01", "No items submitted.");

        for (ApiSellRequest.SoldItems item : request.getSoldItems()) {
            Optional<Product> checkProduct = productRepository.findFirstByIdAndShopId(item.getProductId(), shop.getId());
            if (!checkProduct.isPresent())
                return new ResponseModel("01", "Sorry product (s) not associated with this shop.");
            Product product = checkProduct.get();
            if (item.getQuantity() <= 0)
                return new ResponseModel("01", String.format("Sorry! Invalid Quantity for product %s.", product.getName()));

            if (item.getQuantity() > product.getStock())
                return new ResponseModel("01", String.format("Sorry! Available items in stock for product %s is %s", product.getName(), product.getStock()));

            if (!appSettingService.shouldSellBelowPrices(shop.getId()))
                if (item.getSellingPrice().compareTo(product.getMinSellingPrice()) < 0)
                    return new ResponseModel("01", String.format("Sorry! Selling  price for %s is too low. ", product.getName()));

            total = total.add(item.getSellingPrice().multiply(new BigDecimal(item.getQuantity())));
        }
        BigDecimal mpesaAmount = BigDecimal.ZERO;
        BigDecimal cashAmount = BigDecimal.ZERO;
        BigDecimal creditAmount = BigDecimal.ZERO;

        SalesTransactionPayments mpesaSalesTransactionPayments = null;
        SalesTransactionPayments cashSalesTransactionPayments = null;
        SalesTransactionPayments creditSalesTransactionPayments = null;
        Customer customer = null;
        for (ApiSellRequest.SellPaymentMethod node : request.getPaymentMethods()) {
            PaymentMethods paymentMethod = paymentMethodsRepository.findTopByCode(node.getPaymentMethod());

            if (null == paymentMethod)
                return new ResponseModel("01", "Sorry! Un-supported payment method.");

            ShopPaymentMethods shopPaymentMethod = shopPaymentMethodsRepository.findFirstByShopIdAndPaymentMethodIdAndActive(shop.getId(), paymentMethod.getId(), true);

            if (null == shopPaymentMethod)
                return new ResponseModel("ACTIVATE_CHANNEL", "01", "Sorry! Payment " + paymentMethod.getName() + " is not activated for this shop. Go to settings and activate.");

            if (paymentMethod.getCode().equalsIgnoreCase(PaymentMethods.MPESA) && null == node.getReferenceNo())
                return new ResponseModel("01", "Mpesa ref is required for mpesa payments");

            if (paymentMethod.getCode().equalsIgnoreCase(PaymentMethods.MPESA)) {
                if (total.compareTo(node.getAmount()) < 0)
                    return new ResponseModel("01", "Sorry! Mpesa amount cannot be more than the sale total.");

                mpesaSalesTransactionPayments = new SalesTransactionPayments();
                mpesaSalesTransactionPayments.setPaymentMethod(paymentMethod.getId());
                mpesaSalesTransactionPayments.setAmount(node.getAmount());
                mpesaSalesTransactionPayments.setPaymentReferenceNo(node.getReferenceNo());
                mpesaSalesTransactionPayments.setCashGiven(BigDecimal.ZERO);
                mpesaSalesTransactionPayments.setCashGiven(BigDecimal.ZERO);
                mpesaAmount = node.getAmount();
            } else if (paymentMethod.getCode().equalsIgnoreCase(PaymentMethods.CASH)) {
                cashSalesTransactionPayments = new SalesTransactionPayments();
                cashSalesTransactionPayments.setPaymentMethod(paymentMethod.getId());
                cashSalesTransactionPayments.setAmount(node.getAmount());
                cashSalesTransactionPayments.setCashGiven(request.getCashGiven());
                cashSalesTransactionPayments.setCashChange(request.getChange());
                cashAmount = node.getAmount();
            } else if (paymentMethod.getCode().equalsIgnoreCase(PaymentMethods.CREDIT)) {
                if (total.compareTo(node.getAmount()) < 0)
                    return new ResponseModel("01", "Sorry! Credit amount cannot be more than the sale total.");

                if (null == request.getCustomerPhone() || request.getCustomerPhone().isEmpty()) {
                    // return new ResponseModel("01", "Sorry! Customer details are required for credit payment method.");
                    customer = customerRepository.findDistinctByPhoneAndShopId(Customer.CUSTOMER_DEFUALT_PHONE, request.getShopId());
                    if (null == customer)
                        customer = productService.createDefaultCustomer(user, shop);
                } else {
                    if (!appFunctions.validatePhoneNumber(request.getCustomerPhone()))
                        return new ResponseModel("01", "Sorry! Customer phone number is invalid");
                    request.setCustomerPhone(appFunctions.getInternationalPhoneNumber(request.getCustomerPhone(), ""));
                    customer = customerRepository.findFirstByPhoneAndShopId(request.getCustomerPhone(), request.getShopId());
                    if (null == customer)
                        return new ResponseModel("01", "Sorry! Customer info not found and is required for credit payment method.");
                }
                creditSalesTransactionPayments = new SalesTransactionPayments();
                creditSalesTransactionPayments.setPaymentMethod(paymentMethod.getId());
                creditSalesTransactionPayments.setAmount(node.getAmount());
                creditSalesTransactionPayments.setCashGiven(BigDecimal.ZERO);
                creditSalesTransactionPayments.setCashChange(BigDecimal.ZERO);
                creditAmount = node.getAmount();
            }
        }

        //round up
        total = total.setScale(0, BigDecimal.ROUND_UP);

        logger.info("*********  total summed to compare  with " + total);

        if (total.compareTo(mpesaAmount.add(cashAmount).add(creditAmount)) < 0)
            return new ResponseModel("01", "Sorry! Amount submitted is more than total please correct and submit again.");

        if (total.compareTo(mpesaAmount.add(cashAmount).add(creditAmount)) > 0)
            return new ResponseModel("01", "Sorry! Amount submitted is less than total  please correct and submit again.");


        return productService.apiSell(request, shop, user, cashSalesTransactionPayments, mpesaSalesTransactionPayments, creditSalesTransactionPayments, customer);
    }

    public ResponseModel customers(Long shopId, Integer page, Integer size, HttpServletRequest httpServletRequest) {

        ResponseModel responseModel = shopService.verifyIfValidShop(shopId);
        if (!responseModel.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return responseModel;

        Shop shop = shopService.getShop(shopId);
        Users user = userDetailsService.getAuthicatedUser();

        dashboardService.saveAuditLogMobile("Get customers", shop.getId(), httpServletRequest);

        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUser(shop, user);
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;
        if (!checkIfAllowed(ShopRights.PRIVILEGE_CUSTOMER, shopId))
            return new ResponseModel("01", "Sorry! You are not allowed to perform this operation.");
        logger.info(String.format("============ api shop customers %s page %s size %s ", shopId, page, size));
        page = page != null ? page : 0;
        size = size != null ? size : 20;
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customers = customerRepository.findAllByShopIdOrderByNameAsc(shopId, pageable);
        List<ApiCustomerRes> suppliersList = new ArrayList<>();

        customers.forEach(node -> {
            ApiCustomerRes customer = new ApiCustomerRes();
            customer.setId(node.getId());
            customer.setName(node.getName());
            customer.setPhone(node.getPhone());
            customer.setDescription(node.getDescription() != null ? node.getDescription() : "");
            suppliersList.add(customer);
        });
        return new ResponseModel("00", "success", suppliersList);
    }

    /**
     * edit customer
     */
    public ResponseModel editCustomer(ApiEditCustomerReq request, HttpServletRequest httpServletRequest) {
        ResponseModel response = new ResponseModel();

        ResponseModel responseModel = shopService.verifyIfValidShop(request.getShopId());
        if (!responseModel.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return responseModel;

        Shop shop = shopService.getShop(request.getShopId());
        Users user = userDetailsService.getAuthicatedUser();
        dashboardService.saveAuditLogMobile("Edit/add customer", shop.getId(), httpServletRequest);

        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUser(shop, user);
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;

        if (!checkIfAllowed(ShopRights.PRIVILEGE_CUSTOMER, request.getShopId()))
            return new ResponseModel("01", "Sorry! You are not allowed to perform this operation.");

        if (!appFunctions.validatePhoneNumber(request.getPhone()))
            return new ResponseModel("01", "Sorry! Invalid phone number");

        request.setPhone(appFunctions.getInternationalPhoneNumber(request.getPhone(), ""));

        Customer customer = new Customer();

        response.setMessage("Successfully added new customer.");
        response.setStatus("01");

        if (null != request.getId() && 0 != request.getId()) {
            Optional<Customer> checkCustomer = customerRepository.findById(request.getId());
            if (checkCustomer.isPresent() && !checkCustomer.get().getPhone().equalsIgnoreCase(request.getPhone())) {
                Customer checkCustomerWithSamePhone = customerRepository.findFirstByPhoneAndShopId(request.getPhone(), request.getShopId());
                if (null != checkCustomerWithSamePhone)
                    return new ResponseModel("01", "Sorry customer with similar phone already exists");
            } else if (checkCustomer.isPresent()) {
                customer = checkCustomer.get();
                response.setMessage("Successfully updated customer details.");
            } else {
                response.setMessage("Failed to updated customer details.");
                return response;
            }
        } else {
            Customer checkCustomer = customerRepository.findFirstByPhoneAndShopId(request.getPhone(), request.getShopId());
            if (null != checkCustomer)
                return new ResponseModel("01", "Sorry customer with similar phone already exists");
            customer.setCreatedBy(user.getId());
            customer.setShopId(shop.getId());
            customer.setCreatedOn(new Date());
        }

        customer
                .setPhone(request.getPhone())
                .setName(request.getName())
                .setDescription(request.getDescription())
                .setUpdatedOn(new Date());

        customerRepository.save(customer);
        response.setStatus("00");

        return response;
    }


    /**
     * my expense
     */
    public ResponseModel myExpenses(Long shopId, Integer page, Integer size, HttpServletRequest httpServletRequest) {

        ResponseModel responseModel = shopService.verifyIfValidShop(shopId);
        if (!responseModel.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return responseModel;

        Shop shop = shopService.getShop(shopId);
        Users user = userDetailsService.getAuthicatedUser();
        dashboardService.saveAuditLogMobile("Accessing all expenses", shopId, httpServletRequest);
        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUser(shop, user);
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;
        if (!checkIfAllowed(ShopRights.PRIVILEGE_MY_EXPENSES, shopId))
            return new ResponseModel("01", "Sorry! You are not allowed to perform this operation.");
        logger.info(String.format("============ api shop customers %s page %s size %s ", shopId, page, size));
        page = page != null ? page : 0;
        size = size != null ? size : 20;
        Pageable pageable = PageRequest.of(page, size);
        Page<Expenses> expenses = expensesRepository.findAllByShopIdAndCreatedByOrderByIdDesc(shopId, user.getId(), pageable);
        List<ApiExpenseRes> expenseRes = new ArrayList<>();

        expenses.forEach(node -> {
            ApiExpenseRes apiExpense = new ApiExpenseRes();
            apiExpense.setId(node.getId());
            apiExpense.setName(node.getName());
            apiExpense.setAmount(node.getAmount());
            apiExpense.setAddedOn(appFunctions.formatDateTime(node.getCreatedOn()));
            apiExpense.setDescription(node.getDescription() != null ? node.getDescription() : "");
            expenseRes.add(apiExpense);
        });
        return new ResponseModel("00", "success", expenseRes);
    }


    /**
     * add expense
     */
    public ResponseModel addExpense(ApiExpenseReq request, HttpServletRequest httpServletRequest) {
        ResponseModel responseModel = shopService.verifyIfValidShop(request.getShopId());
        if (!responseModel.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return responseModel;

        Shop shop = shopService.getShop(request.getShopId());
        Users user = userDetailsService.getAuthicatedUser();

        dashboardService.saveAuditLogMobile("Adding expense", request.getShopId(), httpServletRequest);
        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUser(shop, user);
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;

        if (!checkIfAllowed(ShopRights.PRIVILEGE_MY_EXPENSES, request.getShopId()))
            return new ResponseModel("01", "Sorry! You are not allowed to perform this operation.");

        Expenses expenses = new Expenses();
        expenses.setAmount(request.getAmount());
        expenses.setName(request.getName());

        if (null != request.getDescription())
            expenses.setDescription(request.getDescription());

        expenses.setCreatedBy(user.getId());
        expenses.setShopId(request.getShopId());
        expenses.setFlag(AppConstants.ACTIVE_RECORD);
        expensesRepository.save(expenses);

        return new ResponseModel("00", "Expense added successfully.");

    }

    /**
     * search customer
     */
    public ResponseModel findCustomer(Long shopId, String phoneNumber, HttpServletRequest httpServletRequest) {
        ResponseModel responseModel = shopService.verifyIfValidShop(shopId);
        if (!responseModel.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return responseModel;

        Shop shop = shopService.getShop(shopId);
        Users user = userDetailsService.getAuthicatedUser();

        dashboardService.saveAuditLogMobile("Find customer", shopId, httpServletRequest);

        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUser(shop, user);
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;

        if (!appFunctions.validatePhoneNumber(phoneNumber))
            return new ResponseModel("01", "Sorry! Invalid phone number");

        phoneNumber = appFunctions.getInternationalPhoneNumber(phoneNumber, "");

        Customer customer = customerRepository.findFirstByPhoneAndShopId(phoneNumber, shopId);
        if (null == customer)
            return new ResponseModel("01", "Customer details not found");

        return new ResponseModel("00", customer.getName());
    }

    /**
     * set as primary shop
     */
    public ResponseModel setPrimaryShop(Long shopId, HttpServletRequest httpServletRequest) {
        ResponseModel responseModel = shopService.verifyIfValidShop(shopId);
        if (!responseModel.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return responseModel;

        Shop shop = shopService.getShop(shopId);
        Users user = userDetailsService.getAuthicatedUser();

        dashboardService.saveAuditLogMobile("Setting primary shop", shopId, httpServletRequest);

        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUser(shop, user);
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;

        ShopEmployees shopEmployees = shopEmployeesRepository.findFirstByUserIdAndDefaultShop(user.getId(), true);
        if (null != shopEmployees) {
            shopEmployees.setDefaultShop(false);

            shopEmployeesRepository.save(shopEmployees);
        }

        ShopEmployees shopEmployeesUpdate = shopEmployeesRepository.findDistinctTopByUserIdAndShopId(user.getId(), shopId);
        if (null == shopEmployeesUpdate)
            return new ResponseModel("01", "Sorry you don't belong to this shop");

        shopEmployeesUpdate.setDefaultShop(true);
        shopEmployeesRepository.save(shopEmployeesUpdate);

        return new ResponseModel("00", "Shop was successfully set as default");
    }

    /**
     * subscribe
     */
    public ResponseModel subscribe(ApiShopSubscribeRequest request, HttpServletRequest httpServletRequest) {

        Shop shop = shopService.getShop(request.getShopId());

        if (null == shop)
            return new ResponseModel("01", "Sorry shop record not found.");

        if (!appFunctions.validatePhoneNumber(request.getPhone()))
            return new ResponseModel("01", "Sorry invalid phone number.");

        return new ResponseModel("01", "Sorry invalid phone number.");

    }

    /**
     * shop settings
     */
    public ResponseModel shopSettings(Long shopId) {
        ResponseModel responseModel = shopService.verifyIfValidShop(shopId);
        if (!responseModel.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return responseModel;

        Shop shop = shopService.getShop(shopId);
        Users user = userDetailsService.getAuthicatedUser();

        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUser(shop, user);
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;
        if (!checkIfAllowed(ShopRights.PRIVILEGE_SETTINGS, shopId))
            return new ResponseModel("01", "Sorry! You are not allowed to perform this operation.");


        List<PaymentMethods> paymentMethods = paymentMethodsRepository.findAllByIsActive(true);
        List<PaymentMethodsAllowedRes> paymentMethodsAllowedRes = new ArrayList<>();
        for (PaymentMethods paymentMethod : paymentMethods) {
            PaymentMethodsAllowedRes map = new PaymentMethodsAllowedRes();
            map.setName(paymentMethod.getName());
            map.setDescription(paymentMethod.getDescription());
            map.setId(paymentMethod.getId());
            map.setStatus(false);
            ShopPaymentMethods shopPaymentMethod = shopPaymentMethodsRepository.findFirstByShopIdAndPaymentMethodId(shopId, paymentMethod.getId());
            if (null != shopPaymentMethod) {
                map.setStatus(shopPaymentMethod.getActive());
                map.setUpdatedBy(userDetailsService.getAuthicatedUser().getFirstname());
                map.setUpdatedOn(shopPaymentMethod.getCreatedOn());
            }
            paymentMethodsAllowedRes.add(map);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("paymentMethods", paymentMethodsAllowedRes);
        map.put("shop", shopService.getShop(shopId));
        map.put("allowSellBelowPrices", appSettingService.shouldSellBelowPrices(shopId));

        return new ResponseModel("00", "success", map);
    }


    /**
     * shop settings
     */
    public ResponseModel updateSellSetting(Long shopId) {
        ResponseModel responseModel = shopService.verifyIfValidShop(shopId);
        if (!responseModel.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return responseModel;

        Shop shop = shopService.getShop(shopId);
        Users user = userDetailsService.getAuthicatedUser();

        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUser(shop, user);
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;
        if (!checkIfAllowed(ShopRights.PRIVILEGE_SETTINGS, shopId))
            return new ResponseModel("01", "Sorry! You are not allowed to perform this operation.");
        appSettingService.updateSetting(shopId, user.getId());

        return new ResponseModel("00", "Sell setting updated.");
    }

    /**
     * pay debt
     */
    public ResponseModel payDebt(ApiClearDebt request, HttpServletRequest httpServletRequest) {
        Shop shop = shopService.getShop(request.getShopId());

        if (null == shop)
            return new ResponseModel("01", "Sorry shop record not found.");

        dashboardService.saveAuditLogMobile("Clear debt", shop.getId(), httpServletRequest);

        return productService.processPayDebt(request.getId(), request.getAmount());
    }

    public ResponseModel updateShopPaymentMethod(ApiUpdateShopPaymentMethod request) {
        Long shopId = request.getShopId();
        Long paymentMethodId = request.getPaymentMethodId();
        Optional<PaymentMethods> paymentMethods = paymentMethodsRepository.findById(paymentMethodId);
        if (!paymentMethods.isPresent())
            return new ResponseModel("01", "Invalid payment method");

        Users user = userDetailsService.getAuthicatedUser();

        Shop shop = shopService.getShop(shopId);

        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUser(shop, user);
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;
        if (!checkIfAllowed(ShopRights.PRIVILEGE_SETTINGS, shopId))
            return new ResponseModel("01", "Sorry! You are not allowed to perform this operation.");


        PaymentMethods paymentMethod = paymentMethods.get();

        ShopPaymentMethods shopPaymentMethod = shopPaymentMethodsRepository.findFirstByShopIdAndPaymentMethodId(shopId, paymentMethod.getId());
        if (null == shopPaymentMethod) {
            if (!request.getStatus())
                return new ResponseModel("01", "Sorry u cannot disable payment method which already disabled");

            shopPaymentMethod = new ShopPaymentMethods();
            shopPaymentMethod.setCreatedBy(user.getId());
            shopPaymentMethod.setFlag(AppConstants.ACTIVE_RECORD);
            shopPaymentMethod.setCreatedOn(new Date());
            shopPaymentMethod.setActive(true);
            shopPaymentMethod.setPaymentMethodId(paymentMethodId);
            shopPaymentMethod.setShopId(shopId);
        } else {
            if (shopPaymentMethod.getActive() && request.getStatus())
                return new ResponseModel("01", "Sorry no record to update");

            if (!shopPaymentMethod.getActive() && !request.getStatus())
                return new ResponseModel("01", "Sorry no record to update");

            if (shopPaymentMethod.getActive())
                shopPaymentMethod.setActive(false);
            else
                shopPaymentMethod.setActive(true);

        }
        shopPaymentMethod.setCreatedOn(new Date());
        shopPaymentMethod.setCreatedBy(user.getId());
        shopPaymentMethodsRepository.save(shopPaymentMethod);

        return new ResponseModel("00", "Success! Payment method updated");
    }

    /**
     * shop categories
     */
    public ResponseModel shopCategories() {

        List<ApiShopCategoriesRes> apiShopCategoriesRes = new ArrayList<>();
        List<ShopCategories> shopCategories = shopCategoriesRepository.findByFlagOrderByCategoryAsc(AppConstants.ACTIVE_RECORD);
        shopCategories.forEach(node -> {
            ApiShopCategoriesRes category = new ApiShopCategoriesRes();
            category.setCategory(node.getCategory());
            category.setId(node.getId());
            apiShopCategoriesRes.add(category);
        });

        Map<String, Object> map = new HashMap<>();
        map.put("categories", apiShopCategoriesRes);

        return new ResponseModel("00", "success", map);
    }

    /**
     * shop employees
     */
    public ResponseModel shopEmployees(Long shopId) {


        ResponseModel responseModel = shopService.verifyIfValidShop(shopId);
        if (!responseModel.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return responseModel;

        Shop shop = shopService.getShop(shopId);
        Users user = userDetailsService.getAuthicatedUser();

        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUser(shop, user);
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;

        if (!checkIfAllowed(ShopRights.PRIVILEGE_USERS, shopId))
            return new ResponseModel("01", "Sorry! You are not allowed to perform this operation.");

        List<ShopEmployees> shopEmployees = shopEmployeesRepository.findAllByShopId(shopId);
        List<ApiShopEmployeesRes> apiShopEmployeesRes = new ArrayList<>();

        shopEmployees.forEach(node -> {
            Users employeeUser = node.getUserLink();
            ShopEmployeeGroups shopRole = node.getShopEmployeeGroupsLink();
            ApiShopEmployeesRes employee = new ApiShopEmployeesRes();
            employee.setEmpoyeeId(node.getId());
            employee.setFirstName(employeeUser.getFirstname() != null ? employeeUser.getFirstname() : "N/A");
            employee.setLastName(employeeUser.getLastname() != null ? employeeUser.getLastname() : "N?A");
            employee.setPhoneNumber(employeeUser.getPhone());
            employee.setUserGroupId(node.getRoleId());

            if (node.getActive().equals(AppConstants.ACTIVE_RECORD))
                employee.setStatus(AppConstants.MOBILE_ACTIVE_STATUS);
            else
                employee.setStatus(AppConstants.MOBILE_IN_ACTIVE_STATUS);

            employee.setUserGroup(shopRole.getRole());

            if (null != node.getCreateOn())
                employee.setCreatedOn(node.getCreateOn());

            apiShopEmployeesRes.add(employee);
        });

        List<ShopEmployeeGroups> shopEmployeeGroups = shopEmployeeGroupsRepository.findByShopIdAndFlag(shopId, AppConstants.ACTIVE_RECORD);

        List<ApiEmployeesGroupsRes> apiEmployeesGroupsRes = new ArrayList<>();
        shopEmployeeGroups.forEach(node -> {
            ApiEmployeesGroupsRes groupsRes = new ApiEmployeesGroupsRes();
            groupsRes.setGroup(node.getRole());
            groupsRes.setId(node.getId());

            apiEmployeesGroupsRes.add(groupsRes);
        });
        Map<String, Object> map = new HashMap<>();
        map.put("employees", apiShopEmployeesRes);
        map.put("employeeGroups", apiEmployeesGroupsRes);
        map.put("rights", null);

        return new ResponseModel("00", "success", map);
    }

    /**
     * edit shop employee
     */
    public ResponseModel editEmployee(ApiEditEmployeeReq req) {

        ResponseModel responseModel = shopService.verifyIfValidShop(req.getShopId());
        if (!responseModel.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return responseModel;

        Shop shop = shopService.getShop(req.getShopId());
        Users user = userDetailsService.getAuthicatedUser();


        if (!checkIfAllowed(ShopRights.PRIVILEGE_USERS, req.getShopId()))
            return new ResponseModel("01", "Sorry! You are not allowed to perform this operation.");

        ShopEmployees employee = shopEmployeesRepository.findTopByIdAndShopId(req.getEmployeeId(), req.getShopId());
        if (null == employee)
            return new ResponseModel("01", "Sorry employee not available for updates");

        if (employee.getUserId() == user.getId())
            return new ResponseModel("01", "Sorry you cant edit your self");

        if (req.getStatus().equalsIgnoreCase(AppConstants.MOBILE_ACTIVE_STATUS)) {
            ResponseModel checkActiveShopUser = SystemComponent.checkIfShopWillExceedNoOfEmp(shop, 1);
            if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
                return checkActiveShopUser;

            employee.setActive(AppConstants.ACTIVE_RECORD);
        } else
            employee.setActive(AppConstants.SOFT_DELETED);

        Optional<ShopEmployeeGroups> shopEmployeeGroup = shopEmployeeGroupsRepository.findByIdAndShopId(req.getGroupId(), req.getShopId());
        if (!shopEmployeeGroup.isPresent())
            return new ResponseModel("01", "Sorry invalid employee group");

        employee.setRoleId(req.getGroupId());
        shopEmployeesRepository.save(employee);

        return new ResponseModel("00", "Employee edit successfully");
    }


    /**
     * add employee
     *
     * @return String
     */
    public ResponseModel addEmployee(ApiAddEmployeeReq request) {

        ResponseModel response = new ResponseModel();
        ResponseModel responseModel = shopService.verifyIfValidShop(request.getShopId());
        if (!responseModel.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return responseModel;

        Shop shop = shopService.getShop(request.getShopId());
        Users user = userDetailsService.getAuthicatedUser();

        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUser(shop, user);
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;

        if (!checkIfAllowed(ShopRights.PRIVILEGE_USERS, request.getShopId()))
            return new ResponseModel("01", "Sorry! You are not allowed to perform this operation.");
        long shopId = shop.getId();

        Users authicatedUser = userDetailsService.getAuthicatedUser();


        //check if exists
        ResponseModel checkIfExceedEmployees = SystemComponent.checkIfShopWillExceedNoOfEmp(shop, 1);
        if (!checkIfExceedEmployees.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkIfExceedEmployees;


        String code = appFunctions.randomCodeNumber(4);

        if (!appFunctions.validatePhoneNumber(request.getPhone()))
            return new ResponseModel("01", "Sorry! Invalid phone number.");

        request.setPhone(appFunctions.getInternationalPhoneNumber(request.getPhone(), ""));
        String message = null;
        UserTypes userType = userTypeRepository.findFirstByName(UserTypes.PORTAL_USER_TYPE);
        Users newUser = userRepository.findByPhoneAndUserType(request.getPhone(), userType.getId());
        if (null == newUser) {

            Users users = new Users();
            users.setActive(1);
            users.setFirstname(request.getFirstName());
            users.setLastname(request.getLastName());
            users.setPhone(request.getPhone());
            users.setPassword(bCryptPasswordEncoder.encode(code));
            users.setUserType(userType.getId());
            users.setCreatedOn(new Date());
            userRepository.save(users);

            newUser = users;

            Role role = roleRespository.findByRole(Role.ROLE_CREATED);
            //set user role
            UserRole userRole = new UserRole();
            userRole.setRoleId(role.getId());
            userRole.setUserId(newUser.getId());

            userRoleRepository.save(userRole);

            message = (String.format(ApplicationMessages.get("sms.employee.created.mobile"),
                    authicatedUser.getFirstname(), shop.getName(), code, appProperties.playStoreLink));
            response.setMessage("Successfully new created user and added to your business. Message have been sent to ." + request.getPhone());
        } else {
            message = (String.format(ApplicationMessages.get("sms.employee.added.mobile"), authicatedUser.getFirstname(), shop.getName(), appProperties.playStoreLink));
            response.setMessage("Successfully added user to your business. Message have been sent to ." + request.getPhone());
        }

        ShopEmployees checkIfUserAddedToShop = shopEmployeesRepository.findDistinctTopByUserIdAndShopId(newUser.getId(), shopId);
        if (null != checkIfUserAddedToShop) {
            response.setMessage("Sorry! User already exist on this shop.");
            return response;
        }

        //set shop user role
        ShopEmployees shopEmployees = new ShopEmployees();
        shopEmployees.setRoleId(request.getGroupId())
                .setUserId(newUser.getId())
                .setShopId(shopId)
                .setCreatedBy(authicatedUser.getId());
        shopEmployees.setCreateOn(new Date());

        shopEmployeesRepository.save(shopEmployees);


        response.setStatus("00");
        return response;
    }


    /**
     *
     */
    public ResponseModel updateShopDetails(ApiEditShopReq request) {
        Long shopId = request.getShopId();
        Users user = userDetailsService.getAuthicatedUser();
        Shop shop = shopService.getShop(shopId);
        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUser(shop, user);
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;
        if (!checkIfAllowed(ShopRights.PRIVILEGE_SETTINGS, shopId))
            return new ResponseModel("01", "Sorry! You are not allowed to perform this operation.");

        if (null != request.getCategoryId() && request.getCategoryId() != 0L) {
            Optional<ShopCategories> shopCategories = shopCategoriesRepository.findById(request.getCategoryId());
            if (shopCategories.isEmpty())
                return new ResponseModel("01", "Category doesn't exists");
            shop.setCategoryId(request.getCategoryId());

        }

        shop.setName(request.getName())
                .setLocation(request.getLocationName())
                .setUpdatedOn(new Date());
        shopRepository.save(shop);

        return new ResponseModel("00", "Info updated successfully");

    }
}
