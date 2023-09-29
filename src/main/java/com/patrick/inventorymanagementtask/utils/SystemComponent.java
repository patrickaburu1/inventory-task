package com.patrick.inventorymanagementtask.utils;

import com.patrick.inventorymanagementtask.entities.user.Users;
import com.patrick.inventorymanagementtask.entities.ShopPackagePlans;
import com.patrick.inventorymanagementtask.entities.ShopPaymentPackages;
import com.patrick.inventorymanagementtask.entities.products.Shop;
import com.patrick.inventorymanagementtask.entities.products.Stock;
import com.patrick.inventorymanagementtask.entities.products.Suppliers;
import com.patrick.inventorymanagementtask.entities.user.*;
import com.patrick.inventorymanagementtask.repositories.ShopPackagePlansRepository;
import com.patrick.inventorymanagementtask.repositories.product.ShopPaymentPackagesRepository;
import com.patrick.inventorymanagementtask.repositories.product.ShopRepository;
import com.patrick.inventorymanagementtask.repositories.product.StockRepository;
import com.patrick.inventorymanagementtask.repositories.product.SupplierRepository;
import com.patrick.inventorymanagementtask.repositories.user.ShopEmployeesRepository;
import com.patrick.inventorymanagementtask.security.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author patrick on 4/14/20
 * @project myduka-pos
 */
@Component
public class SystemComponent {

    private static SystemComponent instance;

    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private ShopEmployeesRepository shopEmployeesRepository;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private ShopPaymentPackagesRepository shopPaymentPackagesRepository;
    @Autowired
    private ShopPackagePlansRepository shopPackagePlansRepository;

    @Autowired
    private HttpServletRequest _request;


    /**
     * */
    public static Integer getAuthUserId() {
        Users user = instance.userDetailsService.getAuthicatedUser();
        if (null != user)
            return user.getId();

        return 0;
    }

    /**
     * verify user password
     */
    public static Boolean passwordMacthes(String password, Users user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return passwordEncoder.matches(password, user.getPassword());
    }

    /**
     * get last supplier of a products
     */
    public static Suppliers getLastSupplier(Long productId) {
        //  try {
        Stock stock = instance.stockRepository.findFirstByProductIdOrderByIdDesc(productId);
        if (null == stock) {
            return null;
        }
        return instance.supplierRepository.findById(stock.getSupplierId().intValue()).get();
        /*}catch (Exception e){
            return null;
        }*/
    }

    /**
     * check if active user
     */
    public static ResponseModel checkIfActiveUser() {
        Users user = instance.userDetailsService.getAuthicatedUser();
        if (1 != user.getActive() || user.isLocked())
            return new ResponseModel("01", "User account has been suspended.");

        return new ResponseModel("00", "Success");
    }

    /**
     * check if is an active shop
     */
    public static ResponseModel checkActiveShopUser(Shop shop, Users user) {

        if (1 != user.getActive() || user.isLocked())
            return new ResponseModel("01", "User account has been suspended.");

        if (!shop.getFlag().equalsIgnoreCase(AppConstants.ACTIVE_RECORD))
            return new ResponseModel("SHOP_SUSPENDED", "01", "Shop has been suspended. Contact admin for more info");

        if (!shop.getPaymentStatus().equalsIgnoreCase(AppConstants.SHOP_PAYMENT_STATUS_PAID))
            return new ResponseModel("RENEW", "01", "Shop package plan has expired please renew to continue enjoying services.");

        ShopEmployees shopUser = instance.shopEmployeesRepository.findDistinctTopByUserIdAndShopId(user.getId(), shop.getId());

        if (null == shopUser)
            return new ResponseModel("01", "Sorry! You are not authorized employee  of this shop contact shop admin for more info.");

        if (!shopUser.getActive().equalsIgnoreCase(AppConstants.ACTIVE_RECORD))
            return new ResponseModel("01", "Sorry! Your account has been suspended.");

        return new ResponseModel("00", "success verified");

    }

    /**
     * check if an active shop web
     */
    public static ResponseModel checkActiveShopUserWeb() {
        Users user = instance.userDetailsService.getAuthicatedUser();

        if (1 != user.getActive() || user.isLocked())
            return new ResponseModel("01", "User account has been suspended.");

        Long shopId = null;
        if (null != instance._request.getSession().getAttribute("__shopId")) {
            shopId = Long.valueOf(instance._request.getSession().getAttribute("__shopId").toString());
        }
        if (null == shopId)
            return new ResponseModel("SELECT_SHOP", "01", "Sorry select shop to operated from.");

        Optional<Shop> checkShop = instance.shopRepository.findById(shopId);
        if (!checkShop.isPresent())
            return new ResponseModel("01", "Sorry select shop to operate from.");

        Shop shop = checkShop.get();
        if (!shop.getFlag().equalsIgnoreCase(AppConstants.ACTIVE_RECORD))
            return new ResponseModel("SHOP_SUSPENDED", "01", "Shop has been suspended. Contact admin for more info");

        if (!shop.getPaymentStatus().equalsIgnoreCase(AppConstants.SHOP_PAYMENT_STATUS_PAID))
            return new ResponseModel("RENEW", "01", "Shop package plan has expired please renew to continue enjoying services.");

        ShopEmployees shopUser = instance.shopEmployeesRepository.findDistinctTopByUserIdAndShopId(user.getId(), shop.getId());

        if (null == shopUser)
            return new ResponseModel("01", "Sorry! You are not authorized employee  of this shop contact shop admin for more info.");

        if (!shopUser.getActive().equalsIgnoreCase(AppConstants.ACTIVE_RECORD))
            return new ResponseModel("01", "Sorry! Your account has been suspended.");

        return new ResponseModel("00", "success");

    }

    /**
     * check of shop exceeded number of employees
     *
     * @return ResponseModel
     */
    public static ResponseModel checkIfShopExceededNumberOfEmployees(Shop shop, Users user) {
        Integer activeEmployees = instance.shopEmployeesRepository.countAllByShopIdAndActive(shop.getId(), AppConstants.ACTIVE_RECORD);

        Optional<ShopPaymentPackages> shopPaymentPackages;

        if (!shop.getTrial()) {
            Optional<ShopPackagePlans> shopPackagePlans = instance.shopPackagePlansRepository.findById(shop.getPlanId());

            if (!shopPackagePlans.isPresent())
                return new ResponseModel("01", "Your shop don't have an active package plan for this shop.");

            shopPaymentPackages = instance.shopPaymentPackagesRepository.findById(shopPackagePlans.get().getPackageId());
        } else {
            shopPaymentPackages = instance.shopPaymentPackagesRepository.findTopByTrialOrderByIdDesc(true);
        }


        if (shopPaymentPackages.isPresent()) {
            ShopPaymentPackages shopPaymentPackage = shopPaymentPackages.get();

            if (activeEmployees > shopPaymentPackage.getMaxNoOfEmployees())
                return new ResponseModel("UPGRADE", "01", "Sorry shop have exceeded number of employees in the package. Kindly upgrade to continue enjoying services.");

            return new ResponseModel("00", "Success.");
        }
        return new ResponseModel("01", "Your shop don't have an active package.");
    }

    /**
     * check if shop will exceeded number of employees
     *
     * @return ResponseModel
     */
    public static ResponseModel checkIfShopWillExceedNoOfEmp(Shop shop, Integer newEmpCount) {
        Integer activeEmployees = instance.shopEmployeesRepository.countAllByShopIdAndActive(shop.getId(), AppConstants.ACTIVE_RECORD);

        Optional<ShopPaymentPackages> shopPaymentPackages;

        if (!shop.getTrial()) {
            Optional<ShopPackagePlans> shopPackagePlans = instance.shopPackagePlansRepository.findById(shop.getPlanId());

            if (!shopPackagePlans.isPresent())
                return new ResponseModel("01", "Your shop don't have an active package plan.");

            shopPaymentPackages = instance.shopPaymentPackagesRepository.findById(shopPackagePlans.get().getPackageId());
        } else {
            shopPaymentPackages = instance.shopPaymentPackagesRepository.findTopByTrialOrderByIdDesc(true);
        }


        if (shopPaymentPackages.isPresent()) {
            ShopPaymentPackages shopPaymentPackage = shopPaymentPackages.get();

            if (activeEmployees + newEmpCount > shopPaymentPackage.getMaxNoOfEmployees())
                return new ResponseModel("UPGRADE", "01", "Sorry you have reached maximum number of employees. Please upgrade to the next package.");

            return new ResponseModel("00", "Success.");
        }
        return new ResponseModel("01", "Your shop don't have an active package.");
    }


    @PostConstruct
    public void registerInstance() {
        instance = this;
    }
}
