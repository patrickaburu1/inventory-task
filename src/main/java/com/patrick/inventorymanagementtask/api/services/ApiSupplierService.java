package com.patrick.inventorymanagementtask.api.services;

import com.patrick.inventorymanagementtask.api.models.ApiEditSupplierReq;
import com.patrick.inventorymanagementtask.api.models.ApiProductRes;
import com.patrick.inventorymanagementtask.entities.products.Shop;
import com.patrick.inventorymanagementtask.entities.products.Suppliers;
import com.patrick.inventorymanagementtask.entities.user.ShopRights;
import com.patrick.inventorymanagementtask.entities.user.Users;
import com.patrick.inventorymanagementtask.repositories.product.SupplierRepository;
import com.patrick.inventorymanagementtask.security.service.CustomUserDetailsService;
import com.patrick.inventorymanagementtask.service.DashboardService;
import com.patrick.inventorymanagementtask.service.ShopService;
import com.patrick.inventorymanagementtask.utils.AppConstants;
import com.patrick.inventorymanagementtask.utils.AppFunctions;
import com.patrick.inventorymanagementtask.utils.ResponseModel;
import com.patrick.inventorymanagementtask.utils.SystemComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author patrick on 5/18/20
 * @project inventory
 */
@Service
@Transactional
public class ApiSupplierService {

    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private ApiShopService apiShopService;
    @Autowired
    private AppFunctions appFunctions;
    @Autowired
    private ShopService shopService;
    @Autowired
    private DashboardService dashboardService;

    private Logger logger = LoggerFactory.getLogger(getClass());


    public ResponseModel suppliers(Long shopId, Integer page, Integer size, HttpServletRequest httpServletRequest ) {
        if (!apiShopService.checkIfAllowed(ShopRights.PRIVILEGE_SUPPLIER, shopId))
            return new ResponseModel("01", "Sorry! You are not allowed to perform this operation.");
        ResponseModel responseModel = shopService.verifyIfValidShop(shopId);
        if (!responseModel.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return responseModel;

        dashboardService.saveAuditLogMobile("Suppliers", shopId, httpServletRequest);

        Shop shop=shopService.getShop(shopId);
        Users user=userDetailsService.getAuthicatedUser();

        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUser(shop, user);
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;
        logger.info(String.format("============ api shop suppliers %s page %s size %s ",shopId,page,size));
        page = page != null ? page : 0;
        size = size != null ? size : 20;
        Pageable pageable = PageRequest.of(page, size);
        Page<Suppliers> suppliers=supplierRepository.findAllByShopIdOrderByNameAsc(shopId,pageable);
        List< ApiProductRes.LastSupplier> suppliersList=new ArrayList<>();
        suppliers.forEach(node->{
            ApiProductRes.LastSupplier supplier=new ApiProductRes.LastSupplier();
            BeanUtils.copyProperties(node, supplier);
            suppliersList.add(supplier);
        });
        return new ResponseModel("00","success",suppliersList);
    }
    public ResponseModel editSupplier(ApiEditSupplierReq request, HttpServletRequest httpServletRequest ) {
        ResponseModel response = new ResponseModel();
        Users users = userDetailsService.getAuthicatedUser();

        if (!apiShopService.checkIfAllowed(ShopRights.PRIVILEGE_PRODUCTS, request.getShopId()))
            return new ResponseModel("01", "Sorry! You are not allowed to perform this operation.");

        if (!appFunctions.validatePhoneNumber(request.getPhone()))
            return new ResponseModel("01", "Sorry! Invalid phone number");

        dashboardService.saveAuditLogMobile("Edit supplier", request.getShopId(), httpServletRequest);

        request.setPhone(appFunctions.getInternationalPhoneNumber(request.getPhone(), ""));

        Suppliers supplier = new Suppliers();

        response.setMessage("Successfully added new supplier.");

        long shopId = request.getShopId();
        response.setStatus("01");

        if (null != request.getId() && 0 != request.getId()) {
            Optional<Suppliers> checksupplier = supplierRepository.findById(request.getId());

            if (checksupplier.isPresent() && !checksupplier.get().getPhone().equalsIgnoreCase(request.getPhone())){
                Suppliers checkSupplierWithSamePhone = supplierRepository.findFirstByPhoneAndShopId(request.getPhone(), shopId);
                if (null!=checkSupplierWithSamePhone)
                    return new ResponseModel("01", "Sorry unable to update, Supplier with similar phone already exists");
            }
            else if (checksupplier.isPresent()) {
                response.setMessage("Successfully updated supplier details.");
            } else {
                response.setMessage("Failed to updated supplier details.");
                return response;
            }
            supplier=checksupplier.get();
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
}
