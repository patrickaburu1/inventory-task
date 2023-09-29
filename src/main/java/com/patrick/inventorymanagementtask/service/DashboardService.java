package com.patrick.inventorymanagementtask.service;


import com.patrick.inventorymanagementtask.api.models.ApiUpdateProfileReq;
import com.patrick.inventorymanagementtask.api.services.ApiReportsService;
import com.patrick.inventorymanagementtask.entities.*;
import com.patrick.inventorymanagementtask.entities.configs.CreditPayments;
import com.patrick.inventorymanagementtask.entities.products.*;
import com.patrick.inventorymanagementtask.entities.user.*;
import com.patrick.inventorymanagementtask.models.*;
import com.patrick.inventorymanagementtask.models.products.*;
import com.patrick.inventorymanagementtask.properties.ApplicationMessages;
import com.patrick.inventorymanagementtask.properties.ApplicationPropertiesValues;
import com.patrick.inventorymanagementtask.repositories.*;
import com.patrick.inventorymanagementtask.repositories.product.*;
import com.patrick.inventorymanagementtask.repositories.user.*;
import com.patrick.inventorymanagementtask.security.SecurityUtils;
import com.patrick.inventorymanagementtask.security.service.CustomUserDetailsService;
import com.patrick.inventorymanagementtask.utils.*;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.*;

@Service
public class DashboardService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SalesRepository salesRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private RoleRespository roleRespository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ShopRightsRepository shopRightsRepository;

    @Autowired
    private ShopEmployeesGroupRightsRepository shopEmployeesGroupRightsRepository;
    @Autowired
    private PaymentMethodsRepository paymentMethodsRepository;
    @Autowired
    private DebitsRepository debitsRepository;

    @Autowired
    private ClearedDebtsRepository clearedDebtsRepository;

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private HttpServletRequest _request;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopEmployeeGroupsRepository shopEmployeeGroupsRepository;
    @Autowired
    private ShopEmployeesRepository shopEmployeesRepository;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private AppFunctions appFunctions;

    @Autowired
    private ExpensesRepository expensesRepository;
    @Autowired
    private SalesTransactionsRepository salesTransactionsRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ShopPaymentMethodsRepository shopPaymentMethodsRepository;
    @Autowired
    private SalesTransactionPaymentsRepository salesTransactionPaymentsRepository;
    @Autowired
    private CreditPaymentsRepository creditPaymentsRepository;
    @Autowired
    private CreditsRepository creditsRepository;
    @Autowired
    private UserTypeRepository userTypeRepository;
    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ApplicationPropertiesValues appProperties;
    @Autowired
    private ApiReportsService apiReportsService;

    private Logger logger = LoggerFactory.getLogger(getClass());
    @PersistenceContext
    private EntityManager entityManager;

    public Map<String, Object> headers() {
        Map<String, Object> response = new HashMap<>();
        response.put("token", "dmsnkdns");
        response.put("headerName", "nsjdhsd");
        response.put("parameterName", "nsjdhsd");


        Users users = userDetailsService.getAuthicatedUser();

        UserRole userRole = userRoleRepository.findDistinctTopByUserId(users.getId());
        response.put("user", users);
        response.put("role", userRole.getRoleLink().getRole());



      /*  Iterable<Privileges> privileges = privilegesRepository.findAll();
        for (Privileges privilege : privileges) {
            Optional<ShopRolePrivileges> checkPrivilege = shopRolePrivilegesRepository.findAllByRoleIdAndPrivilegeIdAndShopId(userRole.getRoleId(), privilege.getId());
            if (checkPrivilege.isPresent()) {
                response.put(privilege.getCode(), true);
            } else {
                response.put(privilege.getCode(), false);
            }
        }*/

        return response;
    }


    public Map<String, Object> data() {
        Map<String, Object> response = new HashMap<>();

        long shopId = shopService.getShopId();

        response.put("totalSales", salesRepository.totalSales(shopId) != null ? salesRepository.totalSales(shopId) : BigDecimal.ZERO);
        response.put("totalMonthSales", salesRepository.totalMonthSales(shopId) != null ? salesRepository.totalMonthSales(shopId) : BigDecimal.ZERO);
        response.put("todaysTotalSales", salesRepository.todaysTotalSales(shopId) != null ? salesRepository.todaysTotalSales(shopId) : BigDecimal.ZERO);

        response.put("totalProfit", salesRepository.totalProfit(shopId) != null ? salesRepository.totalProfit(shopId) : BigDecimal.ZERO);
        response.put("totalMonthProfit", salesRepository.totalMonthProfit(shopId) != null ? salesRepository.totalMonthProfit(shopId) : BigDecimal.ZERO);
        response.put("todaysTotalProfit", salesRepository.todaysTotalProfit(shopId) != null ? salesRepository.todaysTotalProfit(shopId) : BigDecimal.ZERO);

        response.put("totalProducts", productRepository.countAllByFlagAndShopId(AppConstants.ACTIVE_RECORD, shopId));
        response.put("totalStockWorth", productRepository.getTotalStockWorth(AppConstants.ACTIVE_RECORD, shopId) != null ?
                productRepository.getTotalStockWorth(AppConstants.ACTIVE_RECORD, shopId) : BigDecimal.ZERO);


        response.put("totalProductSoldMonth", salesRepository.totalProductSoldMonth(shopId) != null ? salesRepository.totalProductSoldMonth(shopId) : BigDecimal.ZERO);
        response.put("totalProductSoldToday", salesRepository.totalProductSoldToday(shopId) != null ? salesRepository.totalProductSoldToday(shopId) : BigDecimal.ZERO);


        Session session = entityManager.unwrap(Session.class);


        String queryLent = "SELECT  p.name,  sum(total_amount) as amount from" +
                " sales s inner join products p on s.product = p.id  where p.shop_id=:shopId and p.flag='1'" +
                "group by p.name,p.shop_id  order by sum(total_amount) desc  limit 10";
        List<Object[]> topSaleProducts = session.createSQLQuery(queryLent)
                .setParameter("shopId", shopId)
                .list();

        List<Top10ProductReponse> top10ProductReponses = new ArrayList<>();
        for (int i = 0; i < topSaleProducts.size(); i++) {

            Top10ProductReponse top10ProductReponse = new Top10ProductReponse();


            Object[] _rows = topSaleProducts.get(i);
            String name = String.valueOf(_rows[0]);
            BigDecimal amount = (BigDecimal) _rows[1];

            top10ProductReponse.setProduct(name);
            top10ProductReponse.setRevenue(amount);


            top10ProductReponses.add(top10ProductReponse);
        }
        response.put("topSaleProduct", top10ProductReponses);


        Session session1 = entityManager.unwrap(Session.class);

        String queryLent1 = "SELECT  p.name,  sum(total_profit) as amount from sales s" +
                " inner join products p on s.product = p.id  where p.shop_id=:shopId and p.flag='1' " +
                "group by p.name order by sum(total_profit) desc  limit 10";

        List<Object[]> topProfitProducts = session1.createSQLQuery(queryLent1)
                .setParameter("shopId", shopId)
                .list();

        List<Top10ProductReponse> top10ProfitProductReponses = new ArrayList<>();

        for (int i = 0; i < topProfitProducts.size(); i++) {

            Top10ProductReponse top10ProfitProductReponse = new Top10ProductReponse();


            Object[] _rows = topProfitProducts.get(i);
            String name1 = String.valueOf(_rows[0]);
            BigDecimal amount1 = (BigDecimal) _rows[1];


            top10ProfitProductReponse.setProduct(name1);
            top10ProfitProductReponse.setRevenue(amount1);


            top10ProfitProductReponses.add(top10ProfitProductReponse);
        }


        logger.info("************************ session shop id " + _request.getSession().getAttribute("__shopId"));


        response.put("top10ProfitProduct", top10ProfitProductReponses);
        return response;
    }


    /***
     *
     * sales report
     *
     * @return Map
     * */

    public Map<String, Object> getSalesReport(HttpServletRequest request) {

        Map<String, Object> response = new HashMap<>();

        Session session = entityManager.unwrap(Session.class);
        logger.info("<<<<<<<<<<<<<  current auth user " + SecurityUtils.getCurrentUserLogin());


        String queryLent = "select p.name, count(p.id), p.selling_price  ,sum(total_profit) as profit, sum(s.total_amount) as sales from sales s inner join products p on s.product = p.id\n" +
                "where date(s.created_on)=date(now()) and p.shop_id=:shopId group by s.product order by sum(s.total_amount) desc ";

        List<Object[]> salesReport = session.createSQLQuery(queryLent)
                .setParameter("shopId", shopService.getShopId())
                .list();

        List<SalesReportReponse> salesReportReponses = new ArrayList<>();

        for (int i = 0; i < salesReport.size(); i++) {
            SalesReportReponse sale = new SalesReportReponse();

            Object[] _rows = salesReport.get(i);

            String name = String.valueOf(_rows[0]);
            BigInteger quantity = (BigInteger) _rows[1];
            BigDecimal sp = (BigDecimal) _rows[2];
            BigDecimal profit = (BigDecimal) _rows[2];
            BigDecimal sales = (BigDecimal) _rows[4];

            sale
                    .setName(name)
                    .setQuantity(quantity)
                    .setProfit(profit)
                    .setSale(sales)
                    .setSp(sp);
            salesReportReponses.add(sale);
        }
        response.put("salesReport", salesReportReponses);
        return response;
    }

    /**
     * sales history
     */
    public Map<String, Object> getSalesHistory(HttpServletRequest request) {

        Map<String, Object> response = new HashMap<>();

        Session session = entityManager.unwrap(Session.class);


        String queryLent = "select p.name, p.buying_price, p.selling_price,s.quantity,s.selling_price as sellingAt,s.total_amount,s.total_profit,s.created_on from sales s " +
                "inner join products p on s.product = p.id where date(s.created_on)<=date(now()) and p.shop_id=:shopId order by s.id desc";

        List<Object[]> salesHistory = session.createSQLQuery(queryLent)
                .setParameter("shopId", shopService.getShopId())
                .list();

        List<SalesHistoryResponse> salesHistoryResponses = new ArrayList<>();

        for (int i = 0; i < salesHistory.size(); i++) {
            SalesHistoryResponse sale = new SalesHistoryResponse();

            Object[] _rows = salesHistory.get(i);

            String name = String.valueOf(_rows[0]);
            BigDecimal bp = (BigDecimal) _rows[1];
            BigDecimal sp = (BigDecimal) _rows[2];
            Integer quantity = (Integer) _rows[3];
            BigDecimal soldAt = (BigDecimal) _rows[4];
            BigDecimal sales = (BigDecimal) _rows[5];
            BigDecimal profit = (BigDecimal) _rows[6];
            Date date = (Date) _rows[7];

            sale
                    .setName(name)
                    .setBp(bp)
                    .setSp(sp)
                    .setQuantity(quantity)
                    .setSoldAt(soldAt)
                    .setSale(sales)
                    .setProfit(profit)
                    .setDate(date);
            salesHistoryResponses.add(sale);

        }

        response.put("salesHistory", salesHistoryResponses);
        return response;
    }

    public Map<String, Object> allUsers() {
        Map<String, Object> response = new HashMap<>();

        long shopId = shopService.getShopId();

        // List<Users> users = userRepository.findAll();
        List<ShopEmployeeGroups> userGroups = shopEmployeeGroupsRepository.findByShopIdAndFlag(shopId, AppConstants.ACTIVE_RECORD);

        List<ShopEmployees> shopUsers = shopEmployeesRepository.findAllByShopId(shopId);

        List<UserVm> userVms = new ArrayList<>();
        for (ShopEmployees shopUser : shopUsers) {
            //  UserRole userRole = userRoleRepository.findDistinctTopByUserId(user.getId());

            Users user = shopUser.getUserLink();

            ShopEmployees shopEmployees = shopEmployeesRepository.findDistinctTopByUserIdAndShopId(user.getId(), shopId);

            UserVm userVm = new UserVm();
            userVm
                    .setFirstName(user.getFirstname())
                    .setLastName(user.getLastname())
                    .setRole(shopEmployees.getShopEmployeeGroupsLink().getRole())
                    .setId(user.getId())
                    .setStatus(shopEmployees.getActive());
            userVm.setPhone(user.getPhone());
            userVms.add(userVm);

        }
        Iterable<ShopRights> privileges = shopRightsRepository.findAll();
        response.put("userGroups", userGroups);
        response.put("users", userVms);
        response.put("rights", privileges);

        return response;
    }

    public Map<String, Object> allRoles() {
        Map<String, Object> response = new HashMap<>();

        long shopId = shopService.getShopId();
        List<ShopEmployeeGroups> roles = shopEmployeeGroupsRepository.findByShopIdAndFlag(shopId, AppConstants.ACTIVE_RECORD);

        response.put("roles", roles);
        return response;
    }


    /**
     * add user
     *
     * @return String
     */
    public Map<String, Object> addUser(UserRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "01");

        long shopId = shopService.getShopId();

        Shop shop = shopRepository.findById(shopId).get();

        Users authicatedUser = userDetailsService.getAuthicatedUser();
        UserRole userRoleCheck = userRoleRepository.findDistinctTopByUserId(authicatedUser.getId());

        if (!checkIfAllowed(ShopRights.PRIVILEGE_USERS)) {
            response.put("message", "Sorry not authorized to perform this operation.");
            return response;
        }

        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUserWeb();
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE)) {
            response.put("message", checkActiveShopUser.getStatus());
            return response;
        }

        //check if exists
        ResponseModel checkIfExceedEmployees = SystemComponent.checkIfShopWillExceedNoOfEmp(shop, 1);
        if (!checkIfExceedEmployees.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE)) {
            response.put("status", checkIfExceedEmployees.getStatus());
            response.put("message", checkIfExceedEmployees.getMessage());

            return response;
        }

        String code = appFunctions.randomCodeNumber(4);

        if (!appFunctions.validatePhoneNumber(request.getPhone())) {
            response.put("message", "Sorry! Invalid phone number.");
            return response;
        }
        request.setPhone(appFunctions.getInternationalPhoneNumber(request.getPhone(), ""));
        String message = null;
        UserTypes userType = userTypeRepository.findFirstByName(UserTypes.PORTAL_USER_TYPE);
        Users newUser = userRepository.findByPhoneAndUserType(request.getPhone(), userType.getId());
        if (null == newUser) {

            Users users = new Users();
            users.setActive(1);
            // users.setEmail(request.getPhone());
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
            response.put("message", "Successfully new created user and added to your shop. Message have been sent to ." + request.getPhone());
        } else {
            message = (String.format(ApplicationMessages.get("sms.employee.added.mobile"), authicatedUser.getFirstname(), shop.getName(), appProperties.playStoreLink));
            response.put("message", "Successfully added user to your shop. Message have been sent to ." + request.getPhone());
        }

        ShopEmployees checkIfUserAddedToShop = shopEmployeesRepository.findDistinctTopByUserIdAndShopId(newUser.getId(), shopId);
        if (null != checkIfUserAddedToShop) {
            response.put("message", "Sorry! User already exist on this shop.");
            return response;
        }

        //set shop user role
        ShopEmployees shopEmployees = new ShopEmployees();
        shopEmployees.setRoleId(request.getRole())
                .setUserId(newUser.getId())
                .setShopId(shopId)
                .setCreatedBy(authicatedUser.getId());
        shopEmployees.setCreateOn(new Date());

        shopEmployeesRepository.save(shopEmployees);


        response.put("status", "00");
        return response;
    }


    /**
     * edit user
     *
     * @return String
     */
    public Map<String, Object> editEmployee(EditUserRequest request) {
        Map<String, Object> response = new HashMap<>();

        response.put("status", "01");

        Users authUser = userDetailsService.getAuthicatedUser();
        UserRole userRoleCheck = userRoleRepository.findDistinctTopByUserId(authUser.getId());

        long shopId = shopService.getShopId();


        if (!checkIfAllowed(ShopRights.PRIVILEGE_USERS)) {
            response.put("message", "Sorry you are not authorized to perform this operation.");
            return response;
        }

        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUserWeb();
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE)) {
            response.put("message", checkActiveShopUser.getMessage());
            return response;
        }
        Shop shop = shopService.getShop(shopId);

        Optional<Users> userExists = userRepository.findById(request.getUserId());
        if (!userExists.isPresent()) {
            response.put("message", "Sorry user doesn't exist.");
            return response;
        }
        if (authUser.getId() == request.getUserId()) {
            response.put("message", "Sorry you cannot edit yourself.");
            return response;
        }
        Users editUser = userExists.get();

      /*  editUser.setFirstname(request.getFirstName());
        editUser.setLastname(request.getLastName());
        editUser.setLastname(request.getLastName());*/
        // userRepository.save(editUser);

        ShopEmployees employee = shopEmployeesRepository.findDistinctTopByUserIdAndShopId(editUser.getId(), shopId);

        if (null == employee.getActive()  && request.getStatus().equalsIgnoreCase(AppConstants.ACTIVE_RECORD)){
            ResponseModel checkIfExceedEmployees = SystemComponent.checkIfShopWillExceedNoOfEmp(shop, 1);
            if (!checkIfExceedEmployees.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE)) {
                response.put("status", checkIfExceedEmployees.getStatus());
                response.put("message", checkIfExceedEmployees.getMessage());

                return response;
            }
        }
       else if (!employee.getActive().equalsIgnoreCase(AppConstants.ACTIVE_RECORD) && request.getStatus().equalsIgnoreCase(AppConstants.ACTIVE_RECORD)) {

            ResponseModel checkIfExceedEmployees = SystemComponent.checkIfShopWillExceedNoOfEmp(shop, 1);
            if (!checkIfExceedEmployees.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE)) {
                response.put("status", checkIfExceedEmployees.getStatus());
                response.put("message", checkIfExceedEmployees.getMessage());

                return response;
            }
        }

        employee.setRoleId(request.getRole());
        employee.setUserId(editUser.getId());
        employee.setActive(request.getStatus());
        employee.setUpdatedOn(new Date());
        employee.setUpdatedBy(authUser.getId());

        shopEmployeesRepository.save(employee);
        response.clear();
        response.put("status", "00");
        response.put("message", "User edited successfully.");

        return response;
    }

    /**
     * change  password
     *
     * @return String
     */
    public Map<String, Object> changePassword(String userId, String newPassword) {
        Map<String, Object> response = new HashMap<>();

        Integer userId1 = Integer.parseInt(userId);

        response.put("status", "01");

        Users usersChekc = userDetailsService.getAuthicatedUser();
        UserRole userRoleCheck = userRoleRepository.findDistinctTopByUserId(usersChekc.getId());


        if (!checkIfAllowed(ShopRights.PRIVILEGE_USERS)) {
            response.put("message", "Sorry you are not authorized to perform this operation.");
            return response;
        }

        Optional<Users> userExists = userRepository.findById(userId1);
        if (!userExists.isPresent()) {
            response.put("message", "Sorry you user doen't exist.");
            return response;
        }
        Users changePassword = userExists.get();
        changePassword.setPassword(bCryptPasswordEncoder.encode(newPassword));
        userRepository.save(changePassword);
        response.put("status", "00");
        response.put("message", "Successfully changed password.");
        return response;
    }

    /**
     * create user group and their rights
     */
    public Map<String, Object> createUserGroupAndTheirRights(HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "01");

        String roleName = request.getParameter("role").toUpperCase();

        Long shopId = shopService.getShopId();

        Users users = userDetailsService.getAuthicatedUser();
        if (!checkIfAllowed(ShopRights.PRIVILEGE_USERS)) {
            response.put("status", "403");
            response.put("message", "Sorry your are not allowed to perform this operation.");
            return response;
        }

        ShopEmployeeGroups roles = shopEmployeeGroupsRepository.findByRoleAndShopId(roleName, shopId);
        if (null != roles) {
            response.put("message", "Sorry, Role already exist.");
            return response;
        }

        ShopEmployeeGroups shopRole = new ShopEmployeeGroups();
        shopRole.setRole(roleName);
        shopRole.setCreatedBy(users.getId());
        shopRole.setFlag(AppConstants.ACTIVE_RECORD);
        shopRole.setShopId(shopId);
        shopEmployeeGroupsRepository.save(shopRole);
        Iterable<ShopRights> privileges = shopRightsRepository.findAll();
        for (ShopRights privilege : privileges) {

            String value = request.getParameter(privilege.getCode());

            if (null != value) {
                ShopEmployeesGroupRights setPrivilege = new ShopEmployeesGroupRights();
                setPrivilege
                        .setPrivilegeId(privilege.getId())
                        .setCreatedBy(users.getId())
                        .setRoleId(shopRole.getId())
                        .setCreatedOn(new Date())
                        .setUpdatedOn(new Date())
                        .setShopId(shopService.getShopId());

                shopEmployeesGroupRightsRepository.save(setPrivilege);
            }
            logger.info("<<<<<<<<<<<<<<<  privilege value " + value);
            logger.info("<<<<<<<<<<<<<<<  privilege  " + privilege.getName());
        }

        response.put("status", "00");
        response.put("message", "Successfully added new role you can now assign privilege to the role.");

        return response;

    }

    public Map<String, Object> getJsdata(String start, String end) {
        Map<String, Object> response = new HashMap<>();
        long shopId = shopService.getShopId();

        Shop shop = shopService.getShop(shopId);
        Users user = userDetailsService.getAuthicatedUser();

        ResponseModel checkIfExceedEmployees = SystemComponent.checkIfShopExceededNumberOfEmployees(shop, user);
        if (!checkIfExceedEmployees.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE)) {
            response.put("status", "01");
            response.put("message", checkIfExceedEmployees.getMessage());
            return response;
        }


        List<ShopPaymentMethods> shopPaymentMethods = shopPaymentMethodsRepository.findAllByShopIdAndFlagAndActive(shopId, AppConstants.ACTIVE_RECORD, true);

        List<PaymentModeReport> paymentModeReports = new ArrayList<>();
        for (ShopPaymentMethods shopPaymentMethod : shopPaymentMethods) {

            PaymentMethods paymentMethod = paymentMethodsRepository.findById(shopPaymentMethod.getPaymentMethodId()).get();
            PaymentModeReport paymentModeReport = new PaymentModeReport();
            paymentModeReport.setName(paymentMethod.getName());

            paymentModeReport.setAmount(salesTransactionPaymentsRepository.totalSalesByDateAndPaymentMethodAndShop(paymentMethod.getId(), shopId, AppConstants.ACTIVE_RECORD, start, end) != null ?
                    salesTransactionPaymentsRepository.totalSalesByDateAndPaymentMethodAndShop(paymentMethod.getId(), shopId, AppConstants.ACTIVE_RECORD, start, end) : BigDecimal.ZERO);

            /*paymentModeReport.setAmount(salesRepository.totalSalesByDateAndPaymentMode(paymentMethod.getId(), start, end, shopId) != null ?
                    salesRepository.totalSalesByDateAndPaymentMode(paymentMethod.getId(), start, end, shopId) : BigDecimal.ZERO);*/

            paymentModeReports.add(paymentModeReport);
        }
        PaymentMethods debtPpaymentMethods = paymentMethodsRepository.findTopByCode(PaymentMethods.DEBT);

        BigDecimal totalDebtsBydate = BigDecimal.ZERO;
        if (null != debtPpaymentMethods) {
            totalDebtsBydate = salesRepository.totalSalesByDateAndByPamentMode(debtPpaymentMethods.getId(), start, end, shopId) != null ?
                    salesRepository.totalSalesByDateAndByPamentMode(debtPpaymentMethods.getId(), start, end, shopId) : BigDecimal.ZERO;
        }
        BigDecimal totalClearedDebts = clearedDebtsRepository.clearedShopsDebts(shopId, start, end) != null ? clearedDebtsRepository.clearedShopsDebts(shopId, start, end) : BigDecimal.ZERO;

   /*     BigDecimal totalDeductions = debitsRepository.totalDeductions(start, end, shopId) != null ? debitsRepository.totalDeductions(start, end, shopId) : BigDecimal.ZERO;
        BigDecimal totalDebits = debitsRepository.totalDebits(start, end, shopId) != null ? debitsRepository.totalDebits(start, end, shopId) : BigDecimal.ZERO;
        BigDecimal totalExpenses = debitsRepository.totalExpenses(start, end, shopId) != null ? debitsRepository.totalExpenses(start, end, shopId) : BigDecimal.ZERO;
*/
        BigDecimal unclearedDebts = totalDebtsBydate.subtract(totalClearedDebts);

        BigDecimal totalSalesByDate = salesRepository.totalSalesByDate(start, end, shopId, AppConstants.ACTIVE_RECORD) != null ? salesRepository.totalSalesByDate(start, end, shopId, AppConstants.ACTIVE_RECORD) : BigDecimal.ZERO;
        BigDecimal totalProfitByDate = salesRepository.profitByDate(start, end, shopId, AppConstants.ACTIVE_RECORD) != null ? salesRepository.profitByDate(start, end, shopId, AppConstants.ACTIVE_RECORD) : BigDecimal.ZERO;

        BigDecimal expenses = expensesRepository.totalExpensesPerShop(shopId, start, end) != null ?
                expensesRepository.totalExpensesPerShop(shopId, start, end) : BigDecimal.ZERO;

        BigDecimal purchases = stockRepository.totalPurchasesByDate(shopId, start, end) != null ?
                stockRepository.totalPurchasesByDate(shopId, start, end) : BigDecimal.ZERO;


        BigDecimal debtsCollected = creditPaymentsRepository.totalDebtsCollected(shopId, start, end) != null ?
                creditPaymentsRepository.totalDebtsCollected(shopId, start, end) : BigDecimal.ZERO;

        BigDecimal unPaidDebts = creditsRepository.totalDebtsOutStandingDebts(shopId, AppConstants.ACTIVE_RECORD, start, end, AppConstants.CREDIT_PENDING_PAYMNET, AppConstants.CREDIT_PARTIALLY_PAID) != null ?
                creditsRepository.totalDebtsOutStandingDebts(shopId, AppConstants.ACTIVE_RECORD, start, end, AppConstants.CREDIT_PENDING_PAYMNET, AppConstants.CREDIT_PARTIALLY_PAID) : BigDecimal.ZERO;

        BigDecimal grossMargin = BigDecimal.ZERO;
        if (totalSalesByDate.compareTo(BigDecimal.ZERO) != 0)
            grossMargin = totalSalesByDate.subtract(totalSalesByDate.subtract(totalProfitByDate)).divide(totalSalesByDate, RoundingMode.HALF_DOWN).multiply(new BigDecimal(100));

        response.put("salesByPaymentMode", paymentModeReports);
        response.put("totalSales", totalSalesByDate);

        response.put("salesByDate", totalSalesByDate);

        response.put("productSoldByDate", salesRepository.totalProductSoldByDate(start, end, shopId) != null ? salesRepository.totalProductSoldByDate(start, end, shopId) : BigDecimal.ZERO);

        response.put("totalDebts", totalDebtsBydate);
        response.put("totalClearedDebts", totalClearedDebts);
        response.put("unclearedDebts", unclearedDebts);

       /* response.put("totalDeductions", totalDeductions);
        response.put("totalDebits", totalDebits);*/
        response.put("totalExpenses", expenses);
        response.put("costOfGoodsSold", totalSalesByDate.subtract(totalProfitByDate));
        response.put("profitByDate", totalProfitByDate);
        response.put("debtsCollected", debtsCollected);
        response.put("unPaidDebts", unPaidDebts);
        response.put("grossProfit", totalProfitByDate);
        response.put("netProfit", totalProfitByDate.subtract(expenses));
        response.put("purchases", purchases);
        response.put("grossDiff", totalSalesByDate.subtract(purchases));
        response.put("netDiff", totalSalesByDate.subtract(purchases).subtract(expenses));
        response.put("grossMargin", grossMargin);
        response.put("status", "00");

        return response;
    }

    public ResponseModel graphData(String start, String end) {
        Map<String, Object> response = new HashMap<>();
        long shopId = shopService.getShopId();

        Calendar cal = Calendar.getInstance();
        Date date = appFunctions.formatStringToDate(end);
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);

        Session session = entityManager.unwrap(Session.class);
        StringBuilder salesQuery = new StringBuilder();
        salesQuery
                .append("SELECT ")
                .append("Round(SUM( CASE WHEN YEAR(created_on) = :year AND MONTH(created_on) = 1 AND shop_id = :shopId THEN total_amount ELSE 0 END),2) AS January,")
                .append("Round(SUM( CASE WHEN YEAR(created_on) = :year AND MONTH(created_on) = 2 AND shop_id = :shopId THEN total_amount ELSE 0 END),2) AS February,")
                .append("Round(SUM( CASE WHEN YEAR(created_on) = :year AND MONTH(created_on) = 3 AND shop_id = :shopId THEN total_amount ELSE 0 END),2) AS March,")
                .append("Round(SUM( CASE WHEN YEAR(created_on) = :year AND MONTH(created_on) = 4 AND shop_id = :shopId THEN total_amount ELSE 0 END),2) AS April,")
                .append("Round(SUM( CASE WHEN YEAR(created_on) = :year AND MONTH(created_on) = 5 AND shop_id = :shopId THEN total_amount ELSE 0 END),2) AS May,")
                .append("Round(SUM( CASE WHEN YEAR(created_on) = :year AND MONTH(created_on) = 6 AND shop_id = :shopId THEN total_amount ELSE 0 END),2) AS June,")
                .append("Round(SUM( CASE WHEN YEAR(created_on) = :year AND MONTH(created_on) = 7 AND shop_id = :shopId THEN total_amount ELSE 0 END),2) AS July,")
                .append("Round(SUM( CASE WHEN YEAR(created_on) = :year AND MONTH(created_on) = 8 AND shop_id = :shopId THEN total_amount ELSE 0 END),2) AS August,")
                .append("Round(SUM( CASE WHEN YEAR(created_on) = :year AND MONTH(created_on) = 9 AND shop_id = :shopId THEN total_amount ELSE 0 END),2) AS September,")
                .append("Round(SUM( CASE WHEN YEAR(created_on) = :year AND MONTH(created_on) = 10 AND shop_id = :shopId THEN total_amount ELSE 0 END),2) AS October,")
                .append("Round(SUM( CASE WHEN YEAR(created_on) = :year AND MONTH(created_on) = 11 AND shop_id = :shopId THEN total_amount ELSE 0 END),2) AS November,")
                .append("Round(SUM( CASE WHEN YEAR(created_on) = :year AND MONTH(created_on) = 12 AND shop_id = :shopId THEN total_amount ELSE 0 END),2) AS December")
                .append(" FROM sales");


        StringBuilder profitQuery = new StringBuilder();
        profitQuery
                .append("SELECT ")
                .append("Round(SUM( CASE WHEN YEAR(created_on) = :year AND MONTH(created_on) = 1 AND shop_id = :shopId THEN total_profit ELSE 0 END),2) AS January,")
                .append("Round(SUM( CASE WHEN YEAR(created_on) = :year AND MONTH(created_on) = 2 AND shop_id = :shopId THEN total_profit ELSE 0 END),2) AS February,")
                .append("Round(SUM( CASE WHEN YEAR(created_on) = :year AND MONTH(created_on) = 3 AND shop_id = :shopId THEN total_profit ELSE 0 END),2) AS March,")
                .append("Round(SUM( CASE WHEN YEAR(created_on) = :year AND MONTH(created_on) = 4 AND shop_id = :shopId THEN total_profit ELSE 0 END),2) AS April,")
                .append("Round(SUM( CASE WHEN YEAR(created_on) = :year AND MONTH(created_on) = 5 AND shop_id = :shopId THEN total_profit ELSE 0 END),2) AS May,")
                .append("Round(SUM( CASE WHEN YEAR(created_on) = :year AND MONTH(created_on) = 6 AND shop_id = :shopId THEN total_profit ELSE 0 END),2) AS June,")
                .append("Round(SUM( CASE WHEN YEAR(created_on) = :year AND MONTH(created_on) = 7 AND shop_id = :shopId THEN total_profit ELSE 0 END),2) AS July,")
                .append("Round(SUM( CASE WHEN YEAR(created_on) = :year AND MONTH(created_on) = 8 AND shop_id = :shopId THEN total_profit ELSE 0 END),2) AS August,")
                .append("Round(SUM( CASE WHEN YEAR(created_on) = :year AND MONTH(created_on) = 9 AND shop_id = :shopId THEN total_profit ELSE 0 END),2) AS September,")
                .append("Round(SUM( CASE WHEN YEAR(created_on) = :year AND MONTH(created_on) = 10 AND shop_id = :shopId THEN total_profit ELSE 0 END),2) AS October,")
                .append("Round(SUM( CASE WHEN YEAR(created_on) = :year AND MONTH(created_on) = 11 AND shop_id = :shopId THEN total_profit ELSE 0 END),2) AS November,")
                .append("Round(SUM( CASE WHEN YEAR(created_on) = :year AND MONTH(created_on) = 12 AND shop_id = :shopId THEN total_profit ELSE 0 END),2) AS December")
                .append(" FROM sales");

        StringBuilder expensesQuery = new StringBuilder();
        expensesQuery
                .append("SELECT ")
                .append("Round(SUM( CASE WHEN YEAR(created_on) = :year AND MONTH(created_on) = 1 AND shop_id = :shopId THEN amount ELSE 0 END),2) AS January,")
                .append("Round(SUM( CASE WHEN YEAR(created_on) = :year AND MONTH(created_on) = 2 AND shop_id = :shopId THEN amount ELSE 0 END),2) AS February,")
                .append("Round(SUM( CASE WHEN YEAR(created_on) = :year AND MONTH(created_on) = 3 AND shop_id = :shopId THEN amount ELSE 0 END),2) AS March,")
                .append("Round(SUM( CASE WHEN YEAR(created_on) = :year AND MONTH(created_on) = 4 AND shop_id = :shopId THEN amount ELSE 0 END),2) AS April,")
                .append("Round(SUM( CASE WHEN YEAR(created_on) = :year AND MONTH(created_on) = 5 AND shop_id = :shopId THEN amount ELSE 0 END),2) AS May,")
                .append("Round(SUM( CASE WHEN YEAR(created_on) = :year AND MONTH(created_on) = 6 AND shop_id = :shopId THEN amount ELSE 0 END),2) AS June,")
                .append("Round(SUM( CASE WHEN YEAR(created_on) = :year AND MONTH(created_on) = 7 AND shop_id = :shopId THEN amount ELSE 0 END),2) AS July,")
                .append("Round(SUM( CASE WHEN YEAR(created_on) = :year AND MONTH(created_on) = 8 AND shop_id = :shopId THEN amount ELSE 0 END),2) AS August,")
                .append("Round(SUM( CASE WHEN YEAR(created_on) = :year AND MONTH(created_on) = 9 AND shop_id = :shopId THEN amount ELSE 0 END),2) AS September,")
                .append("Round(SUM( CASE WHEN YEAR(created_on) = :year AND MONTH(created_on) = 10 AND shop_id = :shopId THEN amount ELSE 0 END),2) AS October,")
                .append("Round(SUM( CASE WHEN YEAR(created_on) = :year AND MONTH(created_on) = 11 AND shop_id = :shopId THEN amount ELSE 0 END),2) AS November,")
                .append("Round(SUM( CASE WHEN YEAR(created_on) = :year AND MONTH(created_on) = 12 AND shop_id = :shopId THEN amount ELSE 0 END),2) AS December")
                .append(" FROM expenses where flag=:flag");


        List<Object[]> sales = session.createSQLQuery(salesQuery.toString())
                .setParameter("shopId", shopId)
                .setParameter("year", year).list();

        List<Object[]> profit = session.createSQLQuery(profitQuery.toString())
                .setParameter("shopId", shopId)
                .setParameter("year", year).list();

        List<Object[]> expenses = session.createSQLQuery(expensesQuery.toString())
                .setParameter("shopId", shopId)
                .setParameter("year", year)
                .setParameter("flag", AppConstants.ACTIVE_RECORD)
                .list();

        response.put("sales", sales.get(0));
        response.put("profit", profit.get(0));
        response.put("expenses", expenses.get(0));
        response.put("year", year);

        return new ResponseModel("00", "success", response);
    }

    /**
     * get privileges by id
     */
    public Map<String, Object> getUserGroupRights(int userGroupId) {
        Map<String, Object> response = new HashMap<>();

        Iterable<ShopRights> privileges = shopRightsRepository.findAll();

        long shopId = shopService.getShopId();
        List<PrivilegesResponse> privilegesResponses = new ArrayList<>();

        for (ShopRights privilege : privileges) {
            PrivilegesResponse privilegesResponse = new PrivilegesResponse();
            privilegesResponse.setCode(privilege.getCode());
            privilegesResponse.setName(privilege.getName());
            privilegesResponse.setId(privilege.getId());

            Optional<ShopEmployeesGroupRights> checkIfSet = shopEmployeesGroupRightsRepository.findAllByRoleIdAndPrivilegeIdAndShopId(userGroupId, privilege.getId(), shopId);

            if (checkIfSet.isPresent()) {
                privilegesResponse.setValue(true);
            } else {
                privilegesResponse.setValue(false);
            }
            privilegesResponses.add(privilegesResponse);
        }
        response.put("privileges", privilegesResponses);

        return response;
    }

    /**
     * get privileges by id
     */
    public Map<String, Object> setUserGroupRights(HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();

        Users users = userDetailsService.getAuthicatedUser();

        long shopId = shopService.getShopId();

        response.put("status", "01");

        //check if role exists
        Optional<ShopEmployeeGroups> checkRole = shopEmployeeGroupsRepository.findByIdAndShopId(Long.valueOf(request.getParameter("userGroup")), shopId);

        if (!checkRole.isPresent()) {
            response.put("status", "01");
            response.put("message", "Invalid user group please try again.");
            return response;
        }
        ShopEmployees currentUserRole = shopEmployeesRepository.findDistinctTopByUserIdAndShopId(users.getId(), shopId);
        ShopEmployeeGroups shopRole = checkRole.get();

        Iterable<ShopRights> privileges = shopRightsRepository.findAll();

        //check if trying to chnage super admin roles
        if (shopRole.getRole().equalsIgnoreCase(ShopEmployeeGroups.SHOP_SUPER_ADMIN)) {
            for (ShopRights privilege : privileges) {

                String value = request.getParameter(privilege.getCode());

                //check if role exists
                Optional<ShopEmployeesGroupRights> checkPrivilege = shopEmployeesGroupRightsRepository.findAllByRoleIdAndPrivilegeIdAndShopId(shopRole.getId(),
                        privilege.getId(), shopService.getShopId());
                if (checkPrivilege.isPresent() && null == value) {
                    response.put("message", "Sorry! You can't deny super admin rights.");
                    return response;
                }

            }
        }

        for (ShopRights privilege : privileges) {

            String value = request.getParameter(privilege.getCode());

            //check if role exists
            Optional<ShopEmployeesGroupRights> checkPrivilege = shopEmployeesGroupRightsRepository.findAllByRoleIdAndPrivilegeIdAndShopId(shopRole.getId(),
                    privilege.getId(), shopService.getShopId());
            if (checkPrivilege.isPresent() && null == value) {
                //remove  from privilege
                ShopEmployeesGroupRights removePrivilege = checkPrivilege.get();
                shopEmployeesGroupRightsRepository.delete(removePrivilege);

            } else if (!checkPrivilege.isPresent() && null != value) {
                ShopEmployeesGroupRights setPrivilege = new ShopEmployeesGroupRights();
                setPrivilege
                        .setPrivilegeId(privilege.getId())
                        .setCreatedBy(users.getId())
                        .setRoleId(shopRole.getId())
                        .setCreatedOn(new Date())
                        .setUpdatedOn(new Date())
                        .setShopId(shopService.getShopId());

                shopEmployeesGroupRightsRepository.save(setPrivilege);
            }
            logger.info("<<<<<<<<<<<<<<<  privilege  " + privilege.getName());
            logger.info("<<<<<<<<<<<<<<<  privilege value " + value);
        }
        response.put("status", "00");
        response.put("message", "Success, user group rights updated.");
        return response;
    }

    public boolean checkIfAllowed(String privilege) {

        Long shopId = shopService.getShopId();
        if (null == shopId) {
            return false;
        }
        Users usersCheck = userDetailsService.getAuthicatedUser();
        ShopEmployees userRoleCheck = shopEmployeesRepository.findDistinctTopByUserIdAndShopId(usersCheck.getId(), shopId);

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

    public ModelAndView accessDenied() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("errors/access_denied");
        mv.addObject("title", "Report");
        mv.addObject("_csrf", headers());
        mv.addObject("error", "ACCESS DENIED");
        mv.addObject("message", "ACCESS DENIED");
        return mv;

    }




//    public Object getSalesHistoryByDateExport(String startDateStr, String endDateStr, HttpServletRequest httpServletRequest) {
//        Users users = userDetailsService.getAuthicatedUser();
//        long shopId = shopService.getShopId();
//        ResponseModel responseModel = shopService.verifyIfValidShop(shopId);
//        Shop shop = shopService.getShop(shopId);
//        Users user = userDetailsService.getAuthicatedUser();
//        if (appFunctions.getDiffBetweenDatesLong(endDateStr, startDateStr) < 0)
//            return new ResponseModel("01", "Sorry! Start date cannot be after end date.");
//        Date startDate = appFunctions.formatStringToDate(startDateStr);
//        Date endDate = appFunctions.formatStringToDate(endDateStr);
//
//        //convert endDate time to end of day
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(endDate);
//        calendar.set(Calendar.HOUR_OF_DAY, 23);
//        calendar.set(Calendar.MINUTE, 59);
//        endDate = calendar.getTime();
//
//        if (!responseModel.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
//            return responseModel;
//        else if (null == user.getEmail() || user.getEmail().isEmpty())
//            return new ResponseModel("01", "Failed! \n This report can only be sent via email. Pleased got to profile and update your email address.");
//        else if (!salesRepository.existsAllByShopIdAndCreatedOnIsAfterAndCreatedOnIsBefore(shop.getId(), startDate, endDate))
//            return new ResponseModel("01", "Sorry your business doesn't have sales for specified dates to export.");
//        saveAuditLogWeb("Sales reports export from web", user, httpServletRequest);
//
//        Date finalEndDate = endDate;
//        JobRunner.executeTask(() -> apiReportsService.salesReportExportProcess(shop, user, shopId, startDate, finalEndDate));
//        return new ResponseModel("00", "You will receive report to your email shortly.");
//    }


    public Object getUserReport(String startDate, String endDate) {
        Session session = entityManager.unwrap(Session.class);
        Users users = userDetailsService.getAuthicatedUser();
        logger.info("<<<<<<<<<<<<< accessing user report " + users.getFirstname());

        Long shopId = shopService.getShopId();

        String query = "select u.id , u.firstname ,u.lastname ,\n" +
                "  (select sum(total_amount) from sales where created_by=u.id and shop_id=:shopId and  date(created_on)  between '" + startDate + "' and '" + endDate + "') userSales ,\n" +
                "  (( (select sum(total_amount) from sales where created_by=u.id and  shop_id=:shopId and  date(created_on)  between '" + startDate + "' and '" + endDate + "' )  /" +
                " (select sum(total_amount) from sales where   date(created_on)  between '" + startDate + "' and '" + endDate + "' )) * 100) as salesPercent,\n" +
                "       (select sum(total_profit) from sales where  shop_id=:shopId and created_by=u.id and  date(created_on)  between '" + startDate + "' and '" + endDate + "' ) userProfit,\n" +
                "       (( (select sum(total_profit) from sales where created_by=u.id and  shop_id=:shopId and  date(created_on)  between '" + startDate + "' and '" + endDate + "' )  / " +
                "(select sum(total_profit) from sales where   shop_id=:shopId and  date(created_on)  between '" + startDate + "' and '" + endDate + "' )) * 100) as profitPercent\n" +
                "from user u  left join shop_employees su on su.user_id=u.id where su.shop_id=:shopId";

        logger.info("sql query " + query);
        List<Object[]> userReport = session.createSQLQuery(query)
                .setParameter("shopId", shopId)
                .list();

        Map<String, Object> response = new HashMap<>();

        int recordsTotal = userReport.size();
        int recordsFiltered = userReport.size();

        response.put("recordsTotal", recordsTotal);
        response.put("recordsFiltered", recordsFiltered);
        response.put("draw", 1);
        response.put("data", userReport);

        return response;
    }

    /**
     * save audit log
     */
    public void saveAuditLog(String description, HttpServletRequest httpServletRequest) {

        Users user = userDetailsService.getAuthicatedUser();

        String ip = httpServletRequest.getHeader("X-Forwarded-For") == null ? httpServletRequest.getRemoteAddr() : httpServletRequest.getHeader("X-Forwarded-For");

        AuditLog auditLog = new AuditLog();
        auditLog
                .setCreatedBy(user.getId())
                .setCreatedOn(new Date())
                .setDescription(description)
                .setFlag(AppConstants.ACTIVE_RECORD)
                .setUpdatedOn(new Date())
                .setOrigin(AppConstants.ORIGIN_WEB);

        logger.info("************************ session shop id " + _request.getSession().getAttribute("__shopId"));

        if (null != _request.getSession().getAttribute("__shopId")) {
            auditLog.setShopId(Long.valueOf(_request.getSession().getAttribute("__shopId").toString()));
        }

        auditLog.setIp(ip);
        auditLogRepository.save(auditLog);
    }

    /**
     * save audit log for user in mobile and in
     */
    public void saveAuditLogMobile(String description, Long shopId, HttpServletRequest httpServletRequest) {
        Users user = userDetailsService.getAuthicatedUser();

        String ip = httpServletRequest.getHeader("X-Forwarded-For") == null ? httpServletRequest.getRemoteAddr() : httpServletRequest.getHeader("X-Forwarded-For");

        AuditLog auditLog = new AuditLog();
        auditLog
                .setCreatedBy(user.getId())
                .setCreatedOn(new Date())
                .setDescription(description)
                .setFlag(AppConstants.ACTIVE_RECORD)
                .setUpdatedOn(new Date())
                .setOrigin(AppConstants.ORIGIN_MOBILE);

        if (null != shopId) {
            auditLog.setShopId(shopId);
        }

        auditLog.setIp(ip);
        auditLogRepository.save(auditLog);
    }

    /**
     * save audit log for user in mobile
     */
    public void saveAuditLogMobile(String description, Users user, HttpServletRequest httpServletRequest) {
        String ip = httpServletRequest.getHeader("X-Forwarded-For") == null ? httpServletRequest.getRemoteAddr() : httpServletRequest.getHeader("X-Forwarded-For");

        AuditLog auditLog = new AuditLog();
        auditLog
                .setCreatedBy(user.getId())
                .setCreatedOn(new Date())
                .setDescription(description)
                .setFlag(AppConstants.ACTIVE_RECORD)
                .setUpdatedOn(new Date())
                .setOrigin(AppConstants.ORIGIN_MOBILE);
        auditLog.setIp(ip);

        auditLogRepository.save(auditLog);
    }

    public void saveAuditLogWeb(String description, Users user, HttpServletRequest httpServletRequest) {
        String ip = httpServletRequest.getHeader("X-Forwarded-For") == null ? httpServletRequest.getRemoteAddr() : httpServletRequest.getHeader("X-Forwarded-For");

        AuditLog auditLog = new AuditLog();
        auditLog
                .setCreatedBy(user.getId())
                .setCreatedOn(new Date())
                .setDescription(description)
                .setFlag(AppConstants.ACTIVE_RECORD)
                .setUpdatedOn(new Date())
                .setOrigin(AppConstants.ORIGIN_WEB);


        auditLog.setIp(ip);
        auditLogRepository.save(auditLog);
    }

    /**
     * select shop to operate from web
     */

    public Map<String, Object> selectShop(HttpServletRequest request, Long shopId) {
        Map<String, Object> response = new HashMap<>();
        Optional<Shop> checkShop = shopRepository.findById(shopId);

        Users user = userDetailsService.getAuthicatedUser();


        if (!checkShop.isPresent()) {
            response.put("status", "01");
            response.put("message", "Sorry selected invalid shop please try again later.");
            return response;
        }
        Shop shop = checkShop.get();


        HttpSession session = request.getSession();
        session.setAttribute("__shopId", shop.getId());
        session.setAttribute("_selectedShop", true);
        session.setAttribute("_shopName", shop.getName());
        session.setAttribute("_shop", shop);

        ShopEmployees shopEmployees = shopEmployeesRepository.findDistinctTopByUserIdAndShopId(user.getId(), shopId);

        Iterable<ShopRights> privileges = shopRightsRepository.findAll();
        for (ShopRights privilege : privileges) {

            Optional<ShopEmployeesGroupRights> checkPrivilege = shopEmployeesGroupRightsRepository.findAllByRoleIdAndPrivilegeIdAndShopId(shopEmployees.getRoleId(), privilege.getId(), shopId);
            if (checkPrivilege.isPresent()) {
                //  response.put(privilege.getCode(), true);
                session.setAttribute(privilege.getCode(), true);
            } else {
                session.setAttribute(privilege.getCode(), false);
            }
        }

        response.put("status", "00");
        return response;
    }

    public Map<String, Object> checkIfSelectedIsAvailable() {
        Map<String, Object> response = new HashMap<>();

        if (null != _request.getSession().getAttribute("__shopId")) {
            response.put("status", "00");
            response.put("messages", "Shop already selected");

            return response;
        } else {
            response.put("status", "01");
            response.put("message", "Please select a shop please try again.");
            return response;
        }
    }


    public Map<String, Object> fetchUser(String email) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "01");
        List<Users> user = userRepository.findUsersWhereIsEmailIsLike("%" + email + "%");
        if (null != user) {
            response.put("status", "00");
            response.put("user", user);
            return response;
        }
        return response;
    }






    public ResponseModel myCashBox(String startDate, String endDate) {

        long shopId = Long.parseLong(_request.getSession().getAttribute("__shopId").toString());
        Users user = userDetailsService.getAuthicatedUser();

        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUserWeb();
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;

        List<ShopPaymentMethods> shopPaymentMethods = shopPaymentMethodsRepository.findAllByShopIdAndFlagAndActive(shopId, AppConstants.ACTIVE_RECORD, true);

        List<PaymentModeReport> paymentModeReports = new ArrayList<>();
        for (ShopPaymentMethods shopPaymentMethod : shopPaymentMethods) {
            PaymentMethods paymentMethod = paymentMethodsRepository.findById(shopPaymentMethod.getPaymentMethodId()).get();
            PaymentModeReport paymentModeReport = new PaymentModeReport();
            /*paymentModeReport.setAmount(salesRepository.totalSalesByDateAndPaymentModeAndUserAndShop(paymentMethod.getId(), user.getId(), startDate, endDate, shopId) != null ?
                    salesRepository.totalSalesByDateAndPaymentModeAndUserAndShop(paymentMethod.getId(), user.getId(), startDate, endDate, shopId) : BigDecimal.ZERO);*/
            paymentModeReport.setAmount(salesTransactionPaymentsRepository.totalSalesByDateAndPaymentModeAndUser(paymentMethod.getId(), shopId, AppConstants.ACTIVE_RECORD, user.getId(), startDate, endDate) != null ?
                    salesTransactionPaymentsRepository.totalSalesByDateAndPaymentModeAndUser(paymentMethod.getId(), shopId, AppConstants.ACTIVE_RECORD, user.getId(), startDate, endDate) : BigDecimal.ZERO);
            paymentModeReport.setName(paymentMethod.getName());
            paymentModeReports.add(paymentModeReport);
        }


      /*  BigDecimal total = salesTransactionPaymentsRepository.totalSalesByDateAndPaymentModeAndUserIdAndShopId( user.getId(), shopId, startDate, endDate)  != null ?
                salesTransactionPaymentsRepository.totalSalesByDateAndPaymentModeAndUserIdAndShopId( user.getId(), shopId, startDate, endDate) : BigDecimal.ZERO;*/

        BigDecimal total = salesRepository.totalSalesByDateAndShopAndUser(startDate, endDate, shopId, user.getId(), AppConstants.ACTIVE_RECORD) != null ?
                salesRepository.totalSalesByDateAndShopAndUser(startDate, endDate, shopId, user.getId(), AppConstants.ACTIVE_RECORD) : BigDecimal.ZERO;

        BigDecimal totalDebtsCollected = creditPaymentsRepository.totalUserDebtsCollected(shopId, user.getId(), startDate, endDate) != null ?
                creditPaymentsRepository.totalUserDebtsCollected(shopId, user.getId(), startDate, endDate) : BigDecimal.ZERO;

        BigDecimal expenses = expensesRepository.totalExpensesPerUser(shopId, user.getId(), startDate, endDate) != null ?
                expensesRepository.totalExpensesPerUser(shopId, user.getId(), startDate, endDate) : BigDecimal.ZERO;
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("payments", paymentModeReports);
        map.put("expenses", expenses);
        map.put("debtsCollected", totalDebtsCollected);

        return new ResponseModel("00", "success", map);
    }

    public ResponseModel debtSummary(String startDate, String endDate) {

        long shopId = Long.parseLong(_request.getSession().getAttribute("__shopId").toString());
        Users user = userDetailsService.getAuthicatedUser();

        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUserWeb();
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;

        BigDecimal totalDebts = creditsRepository.totalDebts(shopId, startDate, endDate, AppConstants.ACTIVE_RECORD) != null ?
                creditsRepository.totalDebts(shopId, startDate, endDate, AppConstants.ACTIVE_RECORD) : BigDecimal.ZERO;

        BigDecimal unPaidDebts = creditsRepository.totalDebtsOutStandingDebts(shopId, AppConstants.ACTIVE_RECORD, startDate, endDate, AppConstants.CREDIT_PENDING_PAYMNET, AppConstants.CREDIT_PARTIALLY_PAID) != null ?
                creditsRepository.totalDebtsOutStandingDebts(shopId, AppConstants.ACTIVE_RECORD, startDate, endDate, AppConstants.CREDIT_PENDING_PAYMNET, AppConstants.CREDIT_PARTIALLY_PAID) : BigDecimal.ZERO;

        BigDecimal paidDebts = creditPaymentsRepository.totalUserDebtsCollected(shopId, user.getId(), startDate, endDate) != null ?
                creditPaymentsRepository.totalUserDebtsCollected(shopId, user.getId(), startDate, endDate) : BigDecimal.ZERO;

        Map<String, Object> map = new HashMap<>();
        map.put("totalDebts", totalDebts);
        map.put("unPaidDebts", unPaidDebts);
        map.put("paidDebts", paidDebts);

        return new ResponseModel("00", "success", map);
    }





    public ResponseModel transactionSaleInfo(Long saleTransactionId) {
        long shopId = Long.parseLong(_request.getSession().getAttribute("__shopId").toString());
        Users user = userDetailsService.getAuthicatedUser();
        Map<String, Object> map = new HashMap<>();
        SalesTransactions salesTransaction = salesTransactionsRepository.findById(saleTransactionId).get();

        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUserWeb();
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;

        BigDecimal total = salesRepository.totalBySaleId(saleTransactionId) != null ?
                salesRepository.totalBySaleId(saleTransactionId) : BigDecimal.ZERO;
        if (null != salesTransaction.getCustomerId()) {
            Customer customer = customerRepository.findById(salesTransaction.getCustomerId()).get();
            map.put("customer", customer.getName() + " (" + customer.getPhone() + ")");
        } else {
            map.put("customer", "walk-in customer");
        }
        salesTransaction.setDate(appFunctions.formatDateTime(salesTransaction.getCreatedOn()));

        List<SalesTransactionPayments> salesTransactionPayments = salesTransactionPaymentsRepository.findAllBySalesTransactionId(saleTransactionId);

        if (null != salesTransactionPayments) {
            List<PaymentMethods> paymentMethods = new ArrayList<>();

            for (SalesTransactionPayments salesTransactionPayment : salesTransactionPayments) {
                PaymentMethods paymentMethod = paymentMethodsRepository.findById(salesTransactionPayment.getPaymentMethod()).get();
                paymentMethod.setAmount(salesTransactionPayment.getAmount());
                paymentMethod.setPaymentReferenceNo(salesTransactionPayment.getPaymentReferenceNo() != null ? " --> (" + salesTransactionPayment.getPaymentReferenceNo() + ")" : "");
                paymentMethods.add(paymentMethod);
            }
            map.put("paymentMethod", paymentMethods);

        } else {
            map.put("paymentMethod", null);
        }
        map.put("total", total);
        map.put("transaction", salesTransaction);
        logger.info("***************** transaction sale info");
        return new ResponseModel("00", "success", map);
    }

    public ResponseModel newExpense(Expenses request) {
        long shopId = Long.parseLong(_request.getSession().getAttribute("__shopId").toString());
        Users user = userDetailsService.getAuthicatedUser();

        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUserWeb();
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;

        Expenses expenses = new Expenses();
        expenses.setAmount(request.getAmount());
        expenses.setName(request.getName());

        if (null != request.getDescription())
            expenses.setDescription(request.getDescription());

        expenses.setCreatedBy(user.getId());
        expenses.setShopId(shopId);
        expenses.setFlag(AppConstants.ACTIVE_RECORD);
        expensesRepository.save(expenses);

        return new ResponseModel("00", "Expense added");
    }




    /**
     * sale receipt details
     */

    public ResponseModel saleReceiptInfo(Long saleTransactionId, HttpServletRequest httpServletRequest) {
        long shopId = Long.parseLong(_request.getSession().getAttribute("__shopId").toString());
        Users user = userDetailsService.getAuthicatedUser();
        Map<String, Object> map = new HashMap<>();
        SalesTransactions salesTransaction = salesTransactionsRepository.findById(saleTransactionId).get();

        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUserWeb();
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;

        Shop shop = shopService.getShop(shopId);

        saveAuditLog("Get Sales receipt details for print.", httpServletRequest);

        logger.info("*********** get receipt details for print shop " + shop.getName());
        BigDecimal total = salesRepository.totalBySaleId(saleTransactionId) != null ?
                salesRepository.totalBySaleId(saleTransactionId) : BigDecimal.ZERO;
        if (null != salesTransaction.getCustomerId()) {
            Customer customer = customerRepository.findById(salesTransaction.getCustomerId()).get();
            map.put("customer", customer.getName() + " -- " + customer.getPhone());
        } else {
            map.put("customer", "N/A");
        }
        salesTransaction.setDate(appFunctions.formatDateTime(salesTransaction.getCreatedOn()));

        List<SalesTransactionPayments> salesTransactionPayments = salesTransactionPaymentsRepository.findAllBySalesTransactionId(saleTransactionId);

        if (null != salesTransactionPayments) {
            List<ReceiptPaymentMethods> paymentMethods = new ArrayList<>();

            for (SalesTransactionPayments salesTransactionPayment : salesTransactionPayments) {
                PaymentMethods paymentMethod = paymentMethodsRepository.findById(salesTransactionPayment.getPaymentMethod()).get();

                ReceiptPaymentMethods receiptPaymentMethod = new ReceiptPaymentMethods();
                receiptPaymentMethod.setName(paymentMethod.getName());

                if (paymentMethod.getCode().equalsIgnoreCase(PaymentMethods.CASH))
                    receiptPaymentMethod.setAmount(salesTransactionPayment.getCashGiven());
                else
                    receiptPaymentMethod.setAmount(salesTransactionPayment.getAmount());

                receiptPaymentMethod.setChange(salesTransactionPayment.getCashChange());
                receiptPaymentMethod.setPaymentReferenceNo(salesTransactionPayment.getPaymentReferenceNo() != null ? " --> (" + salesTransactionPayment.getPaymentReferenceNo() + ")" : "");

                paymentMethods.add(receiptPaymentMethod);
            }
            map.put("paymentMethod", paymentMethods);

        } else {
            map.put("paymentMethod", null);
        }

        List<ReceiptProductSoldRes> soldItems = new ArrayList<>();

        List<Sales> sales = salesRepository.findAllBySalesTransactionIdAndFlag(saleTransactionId, AppConstants.ACTIVE_RECORD);
        sales.forEach(node -> {
            ReceiptProductSoldRes soldItem = new ReceiptProductSoldRes();
            soldItem.setProductName(node.getProductLink().getName());
            soldItem.setPrice(node.getSoldAt());
            soldItem.setQuantity(node.getQuantity());
            soldItem.setTotal(node.getTotalAmount());
            soldItems.add(soldItem);
        });

        map.put("soldItems", soldItems);
        map.put("total", total);
        map.put("transaction", salesTransaction);

        return new ResponseModel("00", "success", map);
    }

    /**
     * void sale transaction
     */

    public ResponseModel voidSaleTransaction(VoidTransactionReq req, HttpServletRequest httpServletRequest) {
        long shopId = Long.parseLong(_request.getSession().getAttribute("__shopId").toString());
        Users user = userDetailsService.getAuthicatedUser();
        Map<String, Object> map = new HashMap<>();

        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUserWeb();
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;

        if (!checkIfAllowed(ShopRights.PRIVILEGE_REPORT))
            return new ResponseModel("01", ApplicationMessages.get("response.not.authorized"));

        Shop shop = shopService.getShop(shopId);

        saveAuditLog("Get Sales receipt details for print.", httpServletRequest);

        logger.info("*********** get receipt details for print shop " + shop.getName());

        if (!SystemComponent.passwordMacthes(req.getPassword(), user))
            return new ResponseModel("01", "Sorry password don't math our records.");

        Optional<SalesTransactions> salesTransaction = salesTransactionsRepository.findById(req.getTransactionId());

        if (!salesTransaction.isPresent())
            return new ResponseModel("01", "Sorry transaction to be voided not found.");

        SalesTransactions transaction = salesTransaction.get();

        //check if already voided
        if (!transaction.getStatus().equalsIgnoreCase(AppConstants.ACTIVE_RECORD))
            return new ResponseModel("01", "Sorry transaction already voided.");


        List<SalesTransactionPayments> salesTransactionPayments = salesTransactionPaymentsRepository.findAllBySalesTransactionId(transaction.getId());
        List<SalesTransactionPayments> voidSalesTransactionPayments = new ArrayList<>();


        for (SalesTransactionPayments node : salesTransactionPayments) {
            node.setFlag(AppConstants.VOIDED_TRANSACTION);
            voidSalesTransactionPayments.add(node);

            //check if it was credit and void and if credit was partially paid
            PaymentMethods paymentMethod = paymentMethodsRepository.findById(node.getPaymentMethod()).get();
            if (paymentMethod.getCode().equalsIgnoreCase(PaymentMethods.CREDIT)) {
                Credits credits = creditsRepository.findFirstBySaleTransactionId(transaction.getId());

                CreditPayments creditPayments = creditPaymentsRepository.findFirstByCreditId(credits.getId());

                if (null != creditPayments)
                    return new ResponseModel("01", "Sorry transaction cannot be voided. Its credit has been partially or paid.");

                credits.setFlag(AppConstants.VOIDED_TRANSACTION);
                creditsRepository.save(credits);
            }

        }

        List<Sales> sales = salesRepository.findAllBySalesTransactionId(transaction.getId());
        List<Sales> voidedSales = new ArrayList<>();
        List<Product> products = new ArrayList<>();

        sales.forEach(node -> {
            node.setFlag(AppConstants.VOIDED_TRANSACTION);
            voidedSales.add(node);

            Product product = node.getProductLink();

            //add to product trail
            ProductComponent.trackProductTrailAdd(product, shop, user, node.getQuantity(), "voided", "Product sale voided.");

            product.setStock(product.getStock() + node.getQuantity());
            products.add(product);


        });

        //add void reason && who && add
        transaction.setStatus(AppConstants.VOIDED_TRANSACTION);
        transaction.setVoidedBy(user.getId());
        transaction.setVoidedOn(new Date());
        transaction.setVoidReason(req.getReason());
        salesTransactionsRepository.save(transaction);

        salesTransactionPaymentsRepository.saveAll(voidSalesTransactionPayments);
        salesRepository.saveAll(voidedSales);
        productRepository.saveAll(products);

        return new ResponseModel("00", "Transaction voided successfully.");
    }

    /**
     * update user profile
     */
    public ResponseModel updateProfile(ApiUpdateProfileReq req) {
        Users user = userDetailsService.getAuthicatedUser();
        if (null == user)
            return new ResponseModel("01", "Sorry your are not an active user");

        if (null != req.getEmail() && !req.getEmail().isEmpty()) {
            if (null != user.getEmail() && !user.getEmail().equalsIgnoreCase(req.getEmail())) {
                Users users = userRepository.findByEmail(req.getEmail());
                if (null != users)
                    return new ResponseModel("01", "Email address already exist with another account");
                user.setEmail(req.getEmail());
            } else {
                user.setEmail(req.getEmail());
            }
        }
        user.setFirstname(req.getFirstName());
        user.setLastname(req.getLastName());
        userRepository.save(user);
        return new ResponseModel("00", "Success profile updated successfully.");
    }

    /**
     * change passord
     *
     * @return ResponseModel
     */
    public ResponseModel changePassword(ChangePasswordReq req) {
        Users user = userDetailsService.getAuthicatedUser();
        if (null == user)
            return new ResponseModel("01", "Sorry your are not an active user");

        if (!req.getNewPassword().equalsIgnoreCase(req.getConfirmPassword()))
            return new ResponseModel("01", "Sorry confirm password and new password dont match");

        if (!SystemComponent.passwordMacthes(req.getOldPassword(), user))
            return new ResponseModel("01", "Sorry old password doesn't match our records.");
        if (req.getOldPassword().equals(req.getNewPassword()))
            return new ResponseModel("01", "Sorry new password cannot be the sames as old password.");


        user.setPassword(bCryptPasswordEncoder.encode(req.getNewPassword()));
        userRepository.save(user);

        return new ResponseModel("00", "Success profile updated successfully.");
    }
}
