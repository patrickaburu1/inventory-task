package com.patrick.inventorymanagementtask.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.patrick.inventorymanagementtask.entities.products.Shop;
import com.patrick.inventorymanagementtask.entities.user.ShopEmployees;
import com.patrick.inventorymanagementtask.entities.user.Users;
import com.patrick.inventorymanagementtask.properties.ApplicationMessages;
import com.patrick.inventorymanagementtask.utils.ResponseModel;
import com.patrick.inventorymanagementtask.api.models.AppCreateShopReq;
import com.patrick.inventorymanagementtask.api.models.MpesaStkCallBackReq;
import com.patrick.inventorymanagementtask.config.AppSettingService;
import com.patrick.inventorymanagementtask.entities.*;
import com.patrick.inventorymanagementtask.entities.marketers.Marketers;
import com.patrick.inventorymanagementtask.entities.products.*;
import com.patrick.inventorymanagementtask.entities.transactions.MpesaTransactions;
import com.patrick.inventorymanagementtask.entities.transactions.Transactions;
import com.patrick.inventorymanagementtask.entities.user.*;

import com.patrick.inventorymanagementtask.models.*;
import com.patrick.inventorymanagementtask.models.products.CustomerRequest;
import com.patrick.inventorymanagementtask.properties.ApplicationPropertiesValues;
import com.patrick.inventorymanagementtask.repositories.PaymentPlanEntriesRepository;
import com.patrick.inventorymanagementtask.repositories.ShopCategoriesRepository;
import com.patrick.inventorymanagementtask.repositories.ShopPackagePlansRepository;
import com.patrick.inventorymanagementtask.repositories.ShopPaymentMethodsRepository;
import com.patrick.inventorymanagementtask.repositories.product.*;
import com.patrick.inventorymanagementtask.repositories.transactions.MpesaTransactionRepository;
import com.patrick.inventorymanagementtask.repositories.transactions.TransactionRepository;
import com.patrick.inventorymanagementtask.repositories.transactions.TransactionTypeRepository;
import com.patrick.inventorymanagementtask.repositories.user.*;
import com.patrick.inventorymanagementtask.security.service.CustomUserDetailsService;
import com.patrick.inventorymanagementtask.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;


/**
 * @author patrick on 3/21/20
 * @project  inventory
 */
@Service
@Transactional
public class ShopService {

    @Autowired
    private ShopEmployeeGroupsRepository shopEmployeeGroupsRepository;
    @Autowired
    private ShopEmployeesRepository shopEmployeesRepository;
    @Autowired
    private ShopRightsRepository shopRightsRepository;
    @Autowired
    private ShopEmployeesGroupRightsRepository shopEmployeesGroupRightsRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private PaymentMethodsRepository paymentMethodsRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HttpServletRequest _request;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private ShopPaymentPackagesRepository shopPaymentPackagesRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private AppFunctions appFunctions;

    @Autowired
    private PaymentPlanEntriesRepository paymentPlanEntriesRepository;
    @Autowired
    private ShopPaymentMethodsRepository shopPaymentMethodsRepository;

    @Autowired
    private ShopPackagePlansRepository shopPackagePlansRepository;
    @Autowired
    private ShopCategoriesRepository shopCategoriesRepository;
    @Autowired
    private ProductRepository productRepository;
    private ApplicationPropertiesValues apv;
    @Autowired
    private AppSettingService appSettingService;
    @Autowired
    private TransactionTypeRepository transactionTypeRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private MpesaTransactionRepository mpesaTransactionRepository;


    private Logger logger = LoggerFactory.getLogger(getClass());

    public static String objectToString(Object object) {
        try {
            ObjectWriter ow = new ObjectMapper().writer();
            return ow.writeValueAsString(object);
        } catch (Exception ex) {
            return " An error has occurred " + ex.getMessage();
        }
    }

    public ResponseModel createNewShop(AppCreateShopReq request) {
        ShopPaymentPackages shopPaymentPackage = shopPaymentPackagesRepository.findFirstByTrial(true);
        Users user = userDetailsService.getAuthicatedUser();

        ResponseModel checkActiveShopUser = SystemComponent.checkIfActiveUser();
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;
        //create shop
        Shop shop = new Shop();
        shop
                .setCreatedBy(user.getId())
                .setName(request.getName())
                .setLocation(request.getLocationName())
                .setPhone(user.getPhone())
                .setFlag(AppConstants.ACTIVE_RECORD)
                .setPlanId(shopPaymentPackage.getId());
        shop.setTrial(true);
        shop.setCategoryId(request.getCategoryId());

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        //check if have exceeded number of trial shops
        Integer userTrailShops = shopRepository.countAllByFlagAndCreatedByAndTrial(AppConstants.ACTIVE_RECORD, user.getId(), true);
        Integer maxNumberOfShops = Integer.valueOf(appSettingService.getAppSettings(AppConstants.APP_SETTINGS_MAX_TRIAL_SHOPS).getValue());
        String statusMessage = null;
        String message;

        Marketers marketer = null;


        if (null != marketer) {
            shop.setPaymentStatus(AppConstants.SHOP_PAYMENT_STATUS_UNPAID);
            shop.setMarketerId(marketer.getId());
            shop.setOnOffEarning(marketer.getOnOffEarning());
            shop.setReferralCode(request.getReferralCode());

            statusMessage = "REFERRAL";
            message = "Shop successfully created. Select your preferred package.";
        } else if (userTrailShops >= maxNumberOfShops) {
            shop.setPaymentStatus(AppConstants.SHOP_PAYMENT_STATUS_UNPAID);
            statusMessage = "REACHED_MAX_TRAIL_SHOP";
            message = "Shop successfully created. Select your package.";
        } else {
            cal.add(Calendar.DATE, shopPaymentPackage.getTrialDays());
            message = String.format("Congratulations you have been awarded %s days trial.", shopPaymentPackage.getTrialDays());
        }

        date = cal.getTime();
        shop.setPaymentDueOn(appFunctions.formatDateToStringDateSystemFormat(date));
        //.setPlanId(Long.valueOf(request.getParameter("package")));

        shop = shopRepository.save(shop);

        //set default data for a shop
        setDefaultForShop(user, shop);

        //set up org sms configs
       // webSmsService.setUpBusinessForSms(shop, user);
        return new ResponseModel(statusMessage, "00", message, shop);
    }

    public ResponseModel apiCreateNewShop(AppCreateShopReq request) {
        ShopPaymentPackages shopPaymentPackage = shopPaymentPackagesRepository.findFirstByTrial(true);
        Users user = userDetailsService.getAuthicatedUser();

        Optional<ShopCategories> shopCategory = shopCategoriesRepository.findById(request.getCategoryId());
        if (!shopCategory.isPresent())
            return new ResponseModel("01", "Sorry invalid shop category");

        //create shop
        Shop shop = new Shop();
        shop
                .setCreatedBy(user.getId())
                .setName(request.getName())
                .setLocation(request.getLocation().getAddress())
                .setPhone(user.getPhone())
                .setFlag(AppConstants.ACTIVE_RECORD)
                .setPlanId(shopPaymentPackage.getId());
        shop.setLatitude(request.getLocation().getLatitude());
        shop.setLongitude(request.getLocation().getLongitude());
        shop.setCategoryId(request.getCategoryId());
        //.setPlanId(Long.valueOf(request.getParameter("package")));

        shop.setTrial(true);
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        //check if have exceeded number of trial shops
        Integer userTrailShops = shopRepository.countAllByFlagAndCreatedByAndTrial(AppConstants.ACTIVE_RECORD, user.getId(), true);
        Integer maxNumberOfShops = Integer.valueOf(appSettingService.getAppSettings(AppConstants.APP_SETTINGS_MAX_TRIAL_SHOPS).getValue());
        String statusMessage = null;
        String message;

        Marketers marketer = null;

        //get referral code

        if (null != marketer) {
            shop.setPaymentStatus(AppConstants.SHOP_PAYMENT_STATUS_UNPAID);
            shop.setMarketerId(marketer.getId());
            shop.setReferralCode(request.getReferralCode());
            shop.setOnOffEarning(marketer.getOnOffEarning());
            statusMessage = "REFERRAL";
            message = "Shop successfully created. Select your preferred package.";
        } else if (userTrailShops >= maxNumberOfShops) {
            shop.setPaymentStatus(AppConstants.SHOP_PAYMENT_STATUS_UNPAID);
            statusMessage = "REACHED_MAX_TRAIL_SHOP";
            message = "Shop successfully created. Select your business package.";
        } else {
            cal.add(Calendar.DATE, shopPaymentPackage.getTrialDays());
            message = String.format("Shop successfully created you have been awarded %s days trial.", shopPaymentPackage.getTrialDays());
        }

        date = cal.getTime();
        shop.setPaymentDueOn(appFunctions.formatDateToStringDateSystemFormat(date));
        //.setPlanId(Long.valueOf(request.getParameter("package")));

        shopRepository.save(shop);

        //set default data for a shop
        setDefaultForShop(user, shop);


        return new ResponseModel(statusMessage, "00", message, shop);
    }

    public Map<String, Object> getShops() {

        Users user = userDetailsService.getAuthicatedUser();

        return processGetShops(user);
    }

    public ResponseModel shopsPackages() {
        List<ShopPaymentPackages> shopPaymentPackages = shopPaymentPackagesRepository.findAllByFlag(AppConstants.ACTIVE_RECORD);

        return new ResponseModel("00", "Success", shopPaymentPackages);
    }

    public Map<String, Object> processGetShops(Users user) {
        Map<String, Object> response = new HashMap<>();
        List<ShopResponse> shopResponses = new ArrayList<>();
        List<ShopEmployees> shopUsers = shopEmployeesRepository.findAllByUserIdAndActive(user.getId(), AppConstants.ACTIVE_RECORD);

        for (ShopEmployees shopEmployees : shopUsers) {
            Shop shop = shopRepository.findById(shopEmployees.getShopId()).get();
            ShopResponse shopResponse = new ShopResponse();
            if (shop.getName().length() > 7)
                shopResponse.setName(shop.getName());
                //shopResponse.setName(shop.getName().substring(0, 5) + "..");
            else
                shopResponse.setName(shop.getName());
            shopResponse.setShopPref(shop.getName().substring(0, 2).toUpperCase());
            shopResponse.setId(shop.getId());
            shopResponse.setLocation(shop.getLocation());
            shopResponse.setCreatedOn(shop.getCreatedOn());
            shopResponse.setPaymentDueOn(new Date());
            shopResponse.setFlag(shop.getFlag());
            shopResponse.setAdmin(shop.getCreatedBy() == user.getId());
            shopResponse.setPaymentStatus(shop.getPaymentStatus());
            shopResponse.setHasProducts(ifShopHasProducts(shop));
            if (shop.getPaymentStatus().equalsIgnoreCase(AppConstants.SHOP_PAYMENT_STATUS_PAID))
                shopResponse.setExpired(false);
            else
                shopResponse.setExpired(true);
            if (null != shop.getPaymentDueOn())
                shopResponse.setDueOn(appFunctions.formatDateToDateWithMonthName(shop.getPaymentDueOn()));
            shopResponses.add(shopResponse);
        }
        if (shopResponses.size() > 0)
            response.put("hasShop", true);
        else
            response.put("hasShop", false);

        response.put("data", shopResponses);
        response.put("status", "00");
        response.put("message", "success");
        return response;
    }

    public ResponseModel getShopDetails(Long shopId) {
        Optional<Shop> shop = shopRepository.findById(shopId);
        if (shop.isPresent())
            return new ResponseModel("00", "success", shop.get());
        else
            return new ResponseModel("01", "Sorry Invalid shop ");

    }

    private void setDefaultForShop(Users user, Shop shop) {
        //create super admin shop role
        ShopEmployeeGroups shopEmployeeGroups = new ShopEmployeeGroups();
        shopEmployeeGroups.setShopId(shop.getId())
                .setFlag(AppConstants.ACTIVE_RECORD)
                .setCreatedBy(user.getId())
                .setRole(ShopEmployeeGroups.SHOP_SUPER_ADMIN);
        shopEmployeeGroupsRepository.save(shopEmployeeGroups);


        //create admin user role
        ShopEmployees shopEmployees = new ShopEmployees();
        shopEmployees
                .setRoleId(shopEmployeeGroups.getId())
                .setUserId(user.getId())
                .setShopId(shop.getId())
                .setActive(AppConstants.ACTIVE_RECORD);

        shopEmployees.setCreateOn(new Date());
        shopEmployees.setCreatedBy(user.getId());

        ShopEmployees shopUserRoles = shopEmployeesRepository.findFirstByUserIdAndDefaultShop(user.getId(), true);
        if (null == shopUserRoles) {
            shopEmployees.setDefaultShop(true);
        }
        shopEmployeesRepository.save(shopEmployees);

        //assign rights  to role super admin
        Iterable<ShopRights> privileges = shopRightsRepository.findAll();
        for (ShopRights privilege : privileges) {
            ShopEmployeesGroupRights shopRolePrivilege = new ShopEmployeesGroupRights();
            shopRolePrivilege
                    .setShopId(shop.getId())
                    .setCreatedBy(user.getId())
                    .setPrivilegeId(privilege.getId())
                    .setRoleId(shopEmployeeGroups.getId())
                    .setCreatedOn(new Date())
                    .setUpdatedOn(new Date());
            shopEmployeesGroupRightsRepository.save(shopRolePrivilege);

        }

        //set supplier
        Suppliers suppliers = new Suppliers();
        suppliers
                .setShopId(shop.getId())
                .setEmail(Suppliers.SUPPLIER_DEFUALT_NAME)
                .setDescription(Suppliers.SUPPLIER_DEFUALT_NAME)
                .setName(Suppliers.SUPPLIER_DEFUALT_NAME)
                .setPhone(Suppliers.SUPPLIER_DEFUALT_NAME)
                .setCreatedBy(user.getId());
        supplierRepository.save(suppliers);

        //set shop category
        Category category = new Category();
        category
                .setShopId(shop.getId())
                .setName(Category.DEFAULT_CATEGORY_NAME)
                .setDescription(Category.DEFAULT_CATEGORY_DESCRIPTION);
        categoryRepository.save(category);


        //set cashier employee group
        ShopEmployeeGroups shopCashierGroup = new ShopEmployeeGroups();
        shopCashierGroup.setShopId(shop.getId())
                .setFlag(AppConstants.ACTIVE_RECORD)
                .setCreatedBy(user.getId())
                .setRole(ShopEmployeeGroups.SHOP_SALES_CASHIER);
        shopEmployeeGroupsRepository.save(shopCashierGroup);

        List<ShopRights> shopDefaultRights = shopRightsRepository.findAllByDefaultRight(true);

        //assign rights  to cashier
        shopDefaultRights.forEach(node -> {
            ShopEmployeesGroupRights shopRolePrivilege = new ShopEmployeesGroupRights();
            shopRolePrivilege
                    .setShopId(shop.getId())
                    .setCreatedBy(user.getId())
                    .setPrivilegeId(node.getId())
                    .setRoleId(shopCashierGroup.getId())
                    .setCreatedOn(new Date())
                    .setUpdatedOn(new Date());
            shopEmployeesGroupRightsRepository.save(shopRolePrivilege);

        });

        PaymentMethods paymentMethodCash = paymentMethodsRepository.findTopByCode(PaymentMethods.CASH);
        PaymentMethods paymentMethodMpesa = paymentMethodsRepository.findTopByCode(PaymentMethods.MPESA);

        //set default payments
        if (null != paymentMethodCash) {
            ShopPaymentMethods shopPaymentMethod = new ShopPaymentMethods();
            shopPaymentMethod.setShopId(shop.getId());
            shopPaymentMethod.setPaymentMethodId(paymentMethodCash.getId());
            shopPaymentMethod.setActive(true);
            shopPaymentMethod.setCreatedBy(user.getId());
            shopPaymentMethod.setFlag(AppConstants.ACTIVE_RECORD);
            shopPaymentMethodsRepository.save(shopPaymentMethod);
        }

        //set default payments
        if (null != paymentMethodMpesa) {
            ShopPaymentMethods shopPaymentMethod = new ShopPaymentMethods();
            shopPaymentMethod.setShopId(shop.getId());
            shopPaymentMethod.setPaymentMethodId(paymentMethodMpesa.getId());
            shopPaymentMethod.setActive(true);
            shopPaymentMethod.setFlag(AppConstants.ACTIVE_RECORD);
            shopPaymentMethod.setCreatedBy(user.getId());
            shopPaymentMethodsRepository.save(shopPaymentMethod);
        }
    }

    public ResponseModel updateShop(Shop request) {
        Optional<Shop> checkShop = shopRepository.findById(request.getId());
        if (!checkShop.isPresent()) {
            return new ResponseModel("01", "Invalid shop");
        } else {
            Shop shop = checkShop.get();
            shop.setLocation(request.getLocation())
                    .setName(request.getName())
                    .setPlanId(request.getPlanId());

            shopRepository.save(shop);
            return new ResponseModel("00", "Success");
        }
    }

    public ResponseModel updateBusiness(UpdateBusinessReq req, HttpServletRequest request) {

        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUserWeb();
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;
        long shopId = Long.parseLong(_request.getSession().getAttribute("__shopId").toString());

        if (!appFunctions.validatePhoneNumber(req.getBusinessPhoneNumber()))
            return new ResponseModel("01", "Sorry! Invalid phone number");

        req.setBusinessPhoneNumber(appFunctions.getInternationalPhoneNumber(req.getBusinessPhoneNumber(), ""));

        Optional<ShopCategories> shopCategory = shopCategoriesRepository.findById(req.getCategory());
        if (!shopCategory.isPresent())
            return new ResponseModel("01", "Sorry invalid shop category");

        Shop shop = getShop(shopId);

        shop.setLocation(req.getBusinessAddress())
                .setName(req.getBusinessName())
                .setPhone(req.getBusinessPhoneNumber())
                .setCategoryId(req.getCategory());
        shopRepository.save(shop);

        HttpSession session = request.getSession();
        session.setAttribute("__shopId", shop.getId());
        session.setAttribute("_selectedShop", true);
        session.setAttribute("_shopName", shop.getName());
        session.setAttribute("_shop", shop);

        return new ResponseModel("00", "Business details updated successfully.");

    }

    public Long getShopId() {
        Long shopId = null;
        if (null != _request.getSession().getAttribute("__shopId")) {
            shopId = Long.valueOf(_request.getSession().getAttribute("__shopId").toString());
        }
        return shopId;
    }

    /**
     *
     */
    public Shop getUserDefaultShop(Users user) {
        ShopEmployees defaultShop = shopEmployeesRepository.findFirstByUserIdAndDefaultShopAndActive(user.getId(), true, AppConstants.ACTIVE_RECORD);
        Shop shop = null;
        if (null != defaultShop) {
            shop = shopRepository.findById(defaultShop.getShopId()).get();
            shop.setHasProducts(ifShopHasProducts(shop));
        }
        return shop;
    }

    public ResponseModel verifyIfValidShop(Long shopId) {
        Optional<Shop> shop = shopRepository.findById(shopId);
        if (shop.isPresent()) {
            //check for expired shops
            if (!shop.get().getPaymentStatus().equals(AppConstants.SHOP_PAYMENT_STATUS_PAID))
                return new ResponseModel("RENEW_PLAN", "01", "Sorry your shop plan has expired please renew to continue accessing services.");
            else
                return new ResponseModel("00", "valid shop");
        } else
            return new ResponseModel("01", ApplicationMessages.get("response.invalid.shop"));
    }

    public Shop getShop(Long shopId) {
        Shop shop = null;
        Optional<Shop> checkShop = shopRepository.findById(shopId);
        if (checkShop.isPresent())
            shop = checkShop.get();

        return shop;
    }

    public ResponseModel addCustomer(CustomerRequest request) {
        if (!dashboardService.checkIfAllowed(ShopRights.PRIVILEGE_CUSTOMER)) {
            return new ResponseModel("01", "Sorry you are not authorized to perform this operation");
        }
        if (!appFunctions.validatePhoneNumber(request.getPhone()))
            return new ResponseModel("01", "Sorry! Invalid phone number");

        Users user = userDetailsService.getAuthicatedUser();

        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUserWeb();
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;

        request.setPhone(appFunctions.getInternationalPhoneNumber(request.getPhone(), ""));

        Customer customer = customerRepository.findDistinctByPhoneAndShopId(request.getPhone(), getShopId());
        if (null != customer) {
            return new ResponseModel("01", "Customer already exist with similar phone number.");
        }
        customer = new Customer();
        customer
                .setName(request.getName())
                .setPhone(request.getPhone())
                .setDescription(request.getDescription())
                .setCreatedOn(new Date())
                .setUpdatedOn(new Date())
                .setShopId(getShopId())
                .setCreatedBy(user.getId());
        customerRepository.save(customer);
        return new ResponseModel("00", "Successfully added customer");
    }




    public ResponseModel customerDetails(Long customerId) {
        long shopId = Long.parseLong(_request.getSession().getAttribute("__shopId").toString());

        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUserWeb();
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;

        Customer customer = customerRepository.findTopByIdAndShopId(customerId, shopId);
        if (null == customer)
            return new ResponseModel("01", "Sorry customer record not found.");
        return new ResponseModel("00", "success", customer);
    }

    public ResponseModel editCustomer(CustomerRequest request) {
        if (!dashboardService.checkIfAllowed(ShopRights.PRIVILEGE_CUSTOMER)) {
            return new ResponseModel("01", "Sorry you are not authorized to perform this operation");
        }
        if (!appFunctions.validatePhoneNumber(request.getPhone()))
            return new ResponseModel("01", "Sorry! Invalid phone number");

        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUserWeb();
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;

        request.setPhone(appFunctions.getInternationalPhoneNumber(request.getPhone(), ""));

        Optional<Customer> checkCustomer = customerRepository.findById(request.getId());
        if (!checkCustomer.isPresent()) {
            return new ResponseModel("01", "Sorry invalid customer");
        }
        Customer customer = checkCustomer.get();
        if (!customer.getId().equals(request.getId())) {
            return new ResponseModel("01", "Customer already exist with similar phone number.");
        }
        customer
                .setName(request.getName())
                .setPhone(request.getPhone())
                .setDescription(request.getDescription())
                .setUpdatedOn(new Date());

        customerRepository.save(customer);

        return new ResponseModel("00", "Successfully updated customer info.");
    }

    public ResponseModel suppplierDetails(Integer supplierId) {
        long shopId = Long.parseLong(_request.getSession().getAttribute("__shopId").toString());

        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUserWeb();
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;

        Suppliers supplier = supplierRepository.findFirstByIdAndShopId(supplierId, shopId);
        if (null == supplier)
            return new ResponseModel("01", "Sorry supplier record not found.");
        return new ResponseModel("00", "success", supplier);
    }



    public Map<String, Object> packages() {
        Map<String, Object> packages = new HashMap<>();
        List<ShopPaymentPackages> shopPaymentPackages = shopPaymentPackagesRepository.findAllByFlagAndTrialOrderByDisplayOrderAsc(AppConstants.ACTIVE_RECORD, false);

        List<ShopPackagesWebRes> shopPackagesWebRes = new ArrayList<>();
        shopPaymentPackages.forEach(node -> {
            ShopPackagesWebRes vm = new ShopPackagesWebRes();
            vm.setColorCode(node.getColorCode());
            vm.setDescription(node.getDescription());
            vm.setPackageName(node.getName());
            vm.setId(node.getId());

            List<ShopPackagePlans> shopPackagePlans = shopPackagePlansRepository.findAllByPackageIdAndFlag(node.getId(), AppConstants.ACTIVE_RECORD);
            List<ShopPackagesWebRes.ShopPlansWebRes> shopPlansWebRes = new ArrayList<>();

            shopPackagePlans.forEach(plan -> {
                ShopPackagesWebRes.ShopPlansWebRes plansWebRes = new ShopPackagesWebRes.ShopPlansWebRes();

                plansWebRes.setAmount(plan.getAmount());
                plansWebRes.setDuration(plan.getDuration());
                plansWebRes.setPlan(plan.getPlan());
                plansWebRes.setPlanId(plan.getId());
                shopPlansWebRes.add(plansWebRes);
            });

            vm.setPlans(shopPlansWebRes);
            shopPackagesWebRes.add(vm);
        });

        packages.put("packages", shopPackagesWebRes);
        return packages;
    }


    public ResponseModel availablePaymentMethods() {
        List<PaymentMethods> paymentMethods = paymentMethodsRepository.findAllByIsActive(true);
        List<PaymentMethodsAllowedRes> paymentMethodsAllowedRes = new ArrayList<>();
        Long shopId = getShopId();
        for (PaymentMethods paymentMethod : paymentMethods) {
            PaymentMethodsAllowedRes map = new PaymentMethodsAllowedRes();
            map.setName(paymentMethod.getName());
            map.setDescription(paymentMethod.getDescription());
            map.setId(paymentMethod.getId());
            map.setStatus(false);
            ShopPaymentMethods shopPaymentMethod = shopPaymentMethodsRepository.findFirstByShopIdAndPaymentMethodId(shopId, paymentMethod.getId());
            if (null != shopPaymentMethod) {
                map.setStatus(shopPaymentMethod.getActive());
                map.setUpdatedOn(shopPaymentMethod.getCreatedOn());
                map.setUpdatedBy(userDetailsService.getAuthicatedUser().getFirstname());
            }
            paymentMethodsAllowedRes.add(map);
        }

        return new ResponseModel("00", "success", paymentMethodsAllowedRes);
    }

    /**
     * shop available payment method
     */
    public ResponseModel availablePaymentMethods(Long paymentMethodId) {
        Optional<PaymentMethods> paymentMethods = paymentMethodsRepository.findById(paymentMethodId);
        if (!paymentMethods.isPresent())
            return new ResponseModel("01", "Invalid payment method");

        Long shopId = getShopId();
        Users user = userDetailsService.getAuthicatedUser();

        PaymentMethods paymentMethod = paymentMethods.get();

        ShopPaymentMethods shopPaymentMethod = shopPaymentMethodsRepository.findFirstByShopIdAndPaymentMethodId(shopId, paymentMethod.getId());
        if (null == shopPaymentMethod) {
            shopPaymentMethod = new ShopPaymentMethods();
            shopPaymentMethod.setCreatedBy(user.getId());
            shopPaymentMethod.setFlag(AppConstants.ACTIVE_RECORD);
            shopPaymentMethod.setActive(true);
            shopPaymentMethod.setCreatedOn(new Date());
            shopPaymentMethod.setPaymentMethodId(paymentMethodId);
            shopPaymentMethod.setShopId(shopId);
        } else {
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

    public ResponseModel changeSellSetting() {
        Long shopId = getShopId();
        Users user = userDetailsService.getAuthicatedUser();
        if (!dashboardService.checkIfAllowed(ShopRights.PRIVILEGE_SETTINGS))
            return new ResponseModel("01", "Sorry! You are not allowed to perform this operation.");
        appSettingService.updateSetting(shopId, user.getId());
        return new ResponseModel("00", "Sell Settings updated");
    }

    /**
     * renew via shop package
     */
    public ResponseModel renewPlanPackage(MpesaStkCallBackReq res) {

        MpesaTransactions mpesaTransaction = mpesaTransactionRepository.findFirstByCheckoutId(res.getCheckOutId());
        if (null == mpesaTransaction)
            return new ResponseModel("01", "Mpesa transaction with checkoutId " + res.getCheckOutId() + " not found on mpesa table");

        Transactions transactions = transactionRepository.findFirstByReferenceNo(mpesaTransaction.getReferenceNo());
        if (null == transactions)
            return new ResponseModel("01", "Transaction with ref " + mpesaTransaction.getReferenceNo() + " not found in transactions table");

        ShopPackagePaymentEntries paymentPlanEntry = paymentPlanEntriesRepository.findFirstByRequestId(res.getCheckOutId());
        if (null == paymentPlanEntry)
            return new ResponseModel("01", "Transaction with checkout id " + res.getCheckOutId() + " not found on renewal entries ");

        Shop shop = shopRepository.findById(paymentPlanEntry.getShopId()).get();

        if (res.getStatus().equalsIgnoreCase(AppConstants.MPESA_STATUS_SUCCCESS)) {

            mpesaTransaction.setStatus(AppConstants.TRANSACTION_STATUS_SUCCESSFUL);
            mpesaTransaction.setMpesaRef(res.getMpesaRef());
            mpesaTransaction.setNarration(res.getDescription());
            mpesaTransaction.setUpdatedOn(new Date());
            mpesaTransactionRepository.save(mpesaTransaction);

            transactions.setStatus(AppConstants.TRANSACTION_STATUS_SUCCESSFUL);
            transactions.setNarration(res.getDescription());
            transactions.setUpdatedOn(new Date());
            transactionRepository.save(transactions);

            Integer renewalsCount = paymentPlanEntriesRepository.countAllByShopIdAndStatus(shop.getId(), AppConstants.TRANSACTION_STATUS_SUCCESSFUL);

            //ShopPaymentPackages shopPaymentPackages = shopPaymentPackagesRepository.findById(paymentPlanEntry.getPlanId()).get();
            ShopPackagePlans plan = shopPackagePlansRepository.findById(paymentPlanEntry.getPlanId()).get();
            ShopPaymentPackages shopPaymentPackage = shopPaymentPackagesRepository.findById(plan.getPackageId()).get();
            paymentPlanEntry.setStatus(AppConstants.TRANSACTION_STATUS_SUCCESSFUL);
            paymentPlanEntry.setUpdatedOn(new Date());

            String message;
            String nextPaymentDate;
            Date now = new Date();

            Date lastDueDate = appFunctions.formatStringToDate(shop.getPaymentDueOn());

            String currDate = appFunctions.formatDateToStringDateSystemFormat(now);

            Calendar cal = Calendar.getInstance();

            Integer diffDays = appFunctions.getDiffBetweenDates(shop.getPaymentDueOn(), currDate);
            if (shop.getTrial()) {
                Date date = new Date();

                cal.setTime(date);
                cal.add(Calendar.MONTH, plan.getDuration());
                date = cal.getTime();

                paymentPlanEntry.setLastDue(shop.getPaymentDueOn());
                paymentPlanEntry.setDaysAdded(0);
                paymentPlanEntry.setDaysBeforeDue(0);

                nextPaymentDate = appFunctions.formatDateToStringDateSystemFormat(date);

                shop.setPaymentDueOn(nextPaymentDate);

                message = String.format(ApplicationMessages.get("notification.renewed.sms"), shop.getName(),
                        shopPaymentPackage.getName(), "KES " + plan.getAmount());

            } else if (!shop.getPaymentStatus().equalsIgnoreCase(AppConstants.SHOP_PAYMENT_STATUS_PAID)) {

                Date date = new Date();

                cal.setTime(date);
                cal.add(Calendar.MONTH, plan.getDuration());

                date = cal.getTime();

                paymentPlanEntry.setDaysAdded(0);
                paymentPlanEntry.setDaysBeforeDue(diffDays);

                paymentPlanEntry.setLastDue(shop.getPaymentDueOn());

                nextPaymentDate = appFunctions.formatDateToStringDateSystemFormat(date);

                shop.setPaymentDueOn(nextPaymentDate);

                message = String.format(ApplicationMessages.get("notification.renewed.sms"), shop.getName(),
                        shopPaymentPackage.getName(), "KES " + plan.getAmount());
            } else {
                //extend dates from previous dates

                paymentPlanEntry.setLastDue(shop.getPaymentDueOn());

                ShopPackagePlans newPlan = shopPackagePlansRepository.findById(paymentPlanEntry.getPlanId()).get();

                //check if shop package has changed
                if (!ifShopPlackageHasChanged(shop, newPlan)) {
                    Date nextDue = new Date();
                    cal.setTime(nextDue);
                    cal.add(Calendar.MONTH, plan.getDuration());
                    nextDue = cal.getTime();
                    logger.info("****** added plan months next due on " + nextDue);
                    Integer daysToAdd = upgradeDownGradePackage(shop, newPlan);
                    paymentPlanEntry.setDaysAdded(0);

                    if (daysToAdd > 0) {
                        cal.add(Calendar.DATE, daysToAdd);
                        nextDue = cal.getTime();

                        paymentPlanEntry.setDaysAdded(daysToAdd);
                        logger.info("****** added plan extra next due on " + nextDue);
                        message = String.format(ApplicationMessages.get("notification.renewed.sms.extra.days"), shop.getName(),
                                shopPaymentPackage.getName(), "KES " + plan.getAmount(), daysToAdd);
                    } else {

                        message = String.format(ApplicationMessages.get("notification.renewed.sms"), shop.getName(),
                                shopPaymentPackage.getName(), "KES " + plan.getAmount());
                    }
                    nextPaymentDate = appFunctions.formatDateToStringDateSystemFormat(nextDue);

                    paymentPlanEntry.setDaysBeforeDue(diffDays);


                } else {

                    cal.setTime(lastDueDate);
                    cal.add(Calendar.MONTH, plan.getDuration());
                    lastDueDate = cal.getTime();
                    nextPaymentDate = appFunctions.formatDateToStringDateSystemFormat(lastDueDate);

                    paymentPlanEntry.setDaysAdded(0);
                    paymentPlanEntry.setDaysBeforeDue(diffDays);

                    message = String.format(ApplicationMessages.get("notification.renewed.sms"), shop.getName(),
                            shopPaymentPackage.getName(), "KES " + plan.getAmount());
                }
                //check payment dates
            }


            paymentPlanEntry.setMonthsRenewed(plan.getDuration());
            paymentPlanEntry.setNextDueOn(nextPaymentDate);
            paymentPlanEntriesRepository.save(paymentPlanEntry);

            shop.setPaymentDueOn(nextPaymentDate);
            shop.setPaymentStatus(AppConstants.SHOP_PAYMENT_STATUS_PAID);
            shop.setTrial(false);
            shop.setPlanId(paymentPlanEntry.getPlanId());
            shop = shopRepository.save(shop);

            Users user = userRepository.findById(paymentPlanEntry.getCreatedBy()).get();

            //notify based on number of days
            if (null != shop.getMarketerId() && renewalsCount == 0) {
                ShopPaymentPackages shopPaymentPackages = shopPaymentPackagesRepository.findFirstByTrial(true);
                Integer addTrialDays = shopPaymentPackages.getTrialDays();

                Date wasDue = appFunctions.formatStringToDate(paymentPlanEntry.getNextDueOn());
                cal.setTime(wasDue);
                cal.add(Calendar.DATE, addTrialDays);
                wasDue = cal.getTime();
                nextPaymentDate = appFunctions.formatDateToStringDateSystemFormat(wasDue);
                paymentPlanEntry.setNextDueOn(nextPaymentDate);
                paymentPlanEntry.setDaysAdded(addTrialDays);
                paymentPlanEntriesRepository.save(paymentPlanEntry);

                shop.setPaymentDueOn(nextPaymentDate);
                shop = shopRepository.save(shop);

                message = String.format(ApplicationMessages.get("notification.renewed.sms.extra.days"), shop.getName(),
                        shopPaymentPackage.getName(), "KES " + plan.getAmount(), addTrialDays);


                //if (null != user.getFireBaseToken() && !user.getFireBaseToken().isEmpty())
                  //  notificationService.sendFireBaseNotification(shop, user, user.getFireBaseToken(), "SUCCESS", message, AppConstants.FIREBASE_CONTEXT_RENEW_SUCCESS);

            }
            return new ResponseModel("00", "Transaction received successfully.");
        } else {


            mpesaTransaction.setStatus(AppConstants.TRANSACTION_STATUS_FAILED);
            mpesaTransaction.setNarration(res.getDescription());
            mpesaTransaction.setUpdatedOn(new Date());
            mpesaTransactionRepository.save(mpesaTransaction);

            transactions.setStatus(AppConstants.TRANSACTION_STATUS_FAILED);
            transactions.setNarration(res.getDescription());
            transactions.setUpdatedOn(new Date());
            transactionRepository.save(transactions);

            paymentPlanEntry.setStatus(AppConstants.TRANSACTION_STATUS_FAILED);
            paymentPlanEntry.setUpdatedOn(new Date());
            paymentPlanEntriesRepository.save(paymentPlanEntry);

            Users user = userRepository.findById(paymentPlanEntry.getCreatedBy()).get();
            String message = "Sorry transaction to renew your business plan has failed. Please try again.";

            return new ResponseModel("01", "Transaction callback received as failed and updated");
        }

    }

    private Boolean ifShopPlackageHasChanged(Shop shop, ShopPackagePlans newPlan) {
        ShopPackagePlans previousPlan = shopPackagePlansRepository.findById(shop.getPlanId()).get();
        ShopPaymentPackages previousPackage = shopPaymentPackagesRepository.findById(previousPlan.getPackageId()).get();
        ShopPaymentPackages newPackage = shopPaymentPackagesRepository.findById(newPlan.getPackageId()).get();

        return newPackage.getId().equals(previousPackage.getId());
    }

    public Integer upgradeDownGradePackage(Shop shop, ShopPackagePlans newPlan) {
        ShopPackagePlans previousPlan = shopPackagePlansRepository.findById(shop.getPlanId()).get();
        int daysToAdd;
        logger.info("********** shop is in different package");
        logger.info("********** shop date was due on  " + shop.getPaymentDueOn());

        int daysInAMonth = 30;
        Date now = new Date();
        String currDate = appFunctions.formatDateToStringDateSystemFormat(now);

        //get days diff from now to shop due date
        Integer diffDays = appFunctions.getDiffBetweenDates(shop.getPaymentDueOn(), currDate);
        logger.info("********** days difference from now to due date  " + diffDays);
        //get previous package charge per day ppcpd
        BigDecimal previousCharge = previousPlan.getAmount().divide(new BigDecimal(previousPlan.getDuration() * daysInAMonth), 2);
        logger.info("********** previous plan general amount charge  " + previousPlan.getAmount());
        logger.info("********** previous plan general days  " + previousPlan.getDuration() * daysInAMonth);
        logger.info("********** previous plan charge per day  " + previousCharge);

        //then get new package charge per date npcpd
        BigDecimal newChargePerDay = newPlan.getAmount().divide(new BigDecimal(newPlan.getDuration() * daysInAMonth), 2);

        logger.info("********** new plan general amount charge  " + newPlan.getAmount());
        logger.info("********** new plan general days  " + newPlan.getDuration() * 30);
        logger.info("********** new plan charge per day  " + newChargePerDay);

        //then get days to add (ppcpd / npcpd) * daysDiff
        BigDecimal daysToAddDec = previousCharge.divide(newChargePerDay, 2).multiply(new BigDecimal(diffDays));

        logger.info("********** days to add in big decimal " + daysToAddDec);

        daysToAddDec = daysToAddDec.setScale(0, BigDecimal.ROUND_DOWN);

        logger.info("********** days to add rounded down " + daysToAddDec);

        daysToAdd = daysToAddDec.intValue();

        logger.info("********** days to add integer " + daysToAdd);
        if (daysToAdd > 0)
            return daysToAdd;
        else
            return 0;
    }

    public Boolean ifShopHasProducts(Shop shop) {
        if (null == shop)
            return false;
        Optional<Product> checkProduct = productRepository.findFirstByShopId(shop.getId());
        return checkProduct.isPresent();
    }
}
