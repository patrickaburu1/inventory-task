package com.patrick.inventorymanagementtask.api.services;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.patrick.inventorymanagementtask.api.models.ApiDebtorsRes;
import com.patrick.inventorymanagementtask.api.models.ApiExpenseRes;
import com.patrick.inventorymanagementtask.api.models.ApiSalesReportRes;
import com.patrick.inventorymanagementtask.entities.Credits;
import com.patrick.inventorymanagementtask.entities.Expenses;
import com.patrick.inventorymanagementtask.entities.ShopPaymentMethods;
import com.patrick.inventorymanagementtask.entities.products.*;
import com.patrick.inventorymanagementtask.entities.user.ShopRights;
import com.patrick.inventorymanagementtask.entities.user.Users;
import com.patrick.inventorymanagementtask.models.ExportSalesReportRes;
import com.patrick.inventorymanagementtask.models.products.PaymentModeReport;
import com.patrick.inventorymanagementtask.properties.ApplicationPropertiesValues;
import com.patrick.inventorymanagementtask.repositories.*;
import com.patrick.inventorymanagementtask.repositories.product.CustomerRepository;
import com.patrick.inventorymanagementtask.repositories.product.PaymentMethodsRepository;
import com.patrick.inventorymanagementtask.repositories.product.ProductRepository;
import com.patrick.inventorymanagementtask.repositories.user.UserRepository;
import com.patrick.inventorymanagementtask.security.service.CustomUserDetailsService;
import com.patrick.inventorymanagementtask.service.DashboardService;
import com.patrick.inventorymanagementtask.service.ShopService;
import com.patrick.inventorymanagementtask.utils.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


/**
 * @author patrick on 5/23/20
 * @project inventory
 */
@Service
public class ApiReportsService {
    @Autowired
    private ShopPaymentMethodsRepository shopPaymentMethodsRepository;
    @Autowired
    private PaymentMethodsRepository paymentMethodsRepository;
    @Autowired
    private SalesTransactionPaymentsRepository salesTransactionPaymentsRepository;
    @Autowired
    private SalesRepository salesRepository;
    @Autowired
    private ExpensesRepository expensesRepository;
    @Autowired
    private AppFunctions appFunctions;
    @Autowired
    private ApiShopService apiShopService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CreditPaymentsRepository creditPaymentsRepository;
    @Autowired
    private DashboardService dashboardService;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CreditsRepository creditsRepository;
    @Autowired
    private CustomerRepository customerRepository;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ApplicationPropertiesValues apv;

    public ResponseModel summaryReports(Long shopId, String startDate, String endDate, HttpServletRequest httpServletRequest) {
        if (!apiShopService.checkIfAllowed(ShopRights.PRIVILEGE_REPORT, shopId))
            return new ResponseModel("01", "Sorry! You are not allowed to perform this operation.");
        ResponseModel responseModel = shopService.verifyIfValidShop(shopId);
        if (!responseModel.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return responseModel;

        Shop shop = shopService.getShop(shopId);
        Users user = userDetailsService.getAuthicatedUser();

        ResponseModel checkIfExceed = SystemComponent.checkIfShopExceededNumberOfEmployees(shop, user);
        if (!checkIfExceed.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkIfExceed;

        dashboardService.saveAuditLogMobile("Summary reports", shopId, httpServletRequest);

        logger.info("*********** api reports start date " + startDate);
        logger.info("*********** api reports end date " + endDate);
        Map<String, Object> map = new HashMap<>();

        List<ShopPaymentMethods> shopPaymentMethods = shopPaymentMethodsRepository.findAllByShopIdAndFlagAndActive(shopId, AppConstants.ACTIVE_RECORD, true);
        List<PaymentModeReport> paymentModeReports = new ArrayList<>();
        for (ShopPaymentMethods shopPaymentMethod : shopPaymentMethods) {
            PaymentMethods paymentMethod = paymentMethodsRepository.findById(shopPaymentMethod.getPaymentMethodId()).get();
            PaymentModeReport paymentModeReport = new PaymentModeReport();
            paymentModeReport.setName(paymentMethod.getName());
            paymentModeReport.setAmount(salesTransactionPaymentsRepository.totalSalesByDateAndPaymentMethodAndShop(paymentMethod.getId(), shopId, AppConstants.ACTIVE_RECORD, startDate, endDate) != null ?
                    salesTransactionPaymentsRepository.totalSalesByDateAndPaymentMethodAndShop(paymentMethod.getId(), shopId, AppConstants.ACTIVE_RECORD, startDate, endDate) : BigDecimal.ZERO);
            paymentModeReports.add(paymentModeReport);
        }
        BigDecimal totalSalesByDate = salesRepository.totalSalesByDate(startDate, endDate, shopId, AppConstants.ACTIVE_RECORD) != null ? salesRepository.totalSalesByDate(startDate, endDate, shopId, AppConstants.ACTIVE_RECORD) : BigDecimal.ZERO;
        BigDecimal totalProfitByDate = salesRepository.profitByDate(startDate, endDate, shopId, AppConstants.ACTIVE_RECORD) != null ? salesRepository.profitByDate(startDate, endDate, shopId, AppConstants.ACTIVE_RECORD) : BigDecimal.ZERO;

        BigDecimal expenses = expensesRepository.totalExpensesPerShop(shopId, startDate, endDate) != null ?
                expensesRepository.totalExpensesPerShop(shopId, startDate, endDate) : BigDecimal.ZERO;

        BigDecimal debtsCollected = creditPaymentsRepository.totalDebtsCollected(shopId, startDate, endDate) != null ?
                creditPaymentsRepository.totalDebtsCollected(shopId, startDate, endDate) : BigDecimal.ZERO;

        BigDecimal stockWorth = productRepository.getTotalStockWorth(AppConstants.ACTIVE_RECORD, shopId) != null ?
                productRepository.getTotalStockWorth(AppConstants.ACTIVE_RECORD, shopId) : BigDecimal.ZERO;


        map.put("collectedDebts", debtsCollected);

        map.put("stockWorth", stockWorth);
        map.put("salesByPaymentMethods", paymentModeReports);
        map.put("totalSales", totalSalesByDate);

        map.put("salesByDate", totalSalesByDate);

        map.put("grossProfit", totalProfitByDate);

        map.put("productSoldByDate", salesRepository.totalProductSoldByDate(startDate, endDate, shopId) != null ? salesRepository.totalProductSoldByDate(startDate, endDate, shopId) : BigDecimal.ZERO);

        map.put("totalExpenses", expenses);
        map.put("costOfGoodsSold", totalSalesByDate.subtract(totalProfitByDate));
        map.put("netProfit", totalProfitByDate.subtract(expenses));

        return new ResponseModel("00", "success", map);
    }

    public ResponseModel salesReport(Long shopId, Integer page, Integer size, HttpServletRequest httpServletRequest) {

        page = page != null ? page : 0;
        size = size != null ? size : 20;
        Pageable pageable = PageRequest.of(page, size);

        logger.info("********************** sales report page " + page);
        logger.info("********************** sales report size " + size);
        if (!apiShopService.checkIfAllowed(ShopRights.PRIVILEGE_REPORT, shopId))
            return new ResponseModel("01", "Sorry! You are not allowed to perform this operation.");
        ResponseModel responseModel = shopService.verifyIfValidShop(shopId);
        if (!responseModel.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return responseModel;

        dashboardService.saveAuditLogMobile("Sales reports", shopId, httpServletRequest);

        List<ApiSalesReportRes> reportRes = new ArrayList<>();

        Page<Sales> sales = salesRepository.findAllByShopIdAndFlagOrderByIdDesc(shopId, AppConstants.ACTIVE_RECORD, pageable);
        sales.forEach(node -> {
            ApiSalesReportRes res = new ApiSalesReportRes();
            res.setAmount(node.getSoldAt());
            res.setTotal(node.getTotalAmount());
            res.setQuantity(node.getQuantity());
            res.setSaleDate(appFunctions.formatToHumandateTime(node.getCreatedOn()));
            Optional<Product> product = productRepository.findById(node.getProductId());
            if (product.isPresent()) {
                res.setProduct(product.get().getName());
                reportRes.add(res);
            }
        });

        logger.info("*********** api sale report reports page " + page);
        return new ResponseModel("00", "success", reportRes);
    }

    /**
     * export sales report
     */
    public ResponseModel salesReportExport(Long shopId, String startDateStr, String endDateStr, HttpServletRequest httpServletRequest) {
        if (!apiShopService.checkIfAllowed(ShopRights.PRIVILEGE_REPORT, shopId))
            return new ResponseModel("01", "Sorry! You are not allowed to perform this operation.");
        ResponseModel responseModel = shopService.verifyIfValidShop(shopId);
        Shop shop = shopService.getShop(shopId);
        Users user = userDetailsService.getAuthicatedUser();
        if (appFunctions.getDiffBetweenDatesLong(endDateStr, startDateStr)< 0)
            return new ResponseModel("01", "Sorry! Start date cannot be after end date.");
        Date startDate = appFunctions.formatStringToDate(startDateStr);
        Date endDate = appFunctions.formatStringToDate(endDateStr);

        //convert endDate time to end of day
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        endDate = calendar.getTime();

        if (!responseModel.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return responseModel;
        else if (null == user.getEmail() || user.getEmail().isEmpty())
            return new ResponseModel("01", "Failed! \n This report can only be sent via email. Pleased got to profile and update your email address.");
        else if (!salesRepository.existsAllByShopIdAndCreatedOnIsAfterAndCreatedOnIsBefore(shop.getId(), startDate, endDate))
            return new ResponseModel("01", "Sorry your business doesn't have sales for specified dates to export.");
        dashboardService.saveAuditLogMobile("Sales reports export from mobile", shopId, httpServletRequest);

        Date finalEndDate = endDate;
        JobRunner.executeTask(() -> salesReportExportProcess(shop, user, shopId, startDate, finalEndDate));
        return new ResponseModel("00", "You will receive report to your email shortly.");
    }

    /**
     * export sales report process
     */
    public ResponseModel salesReportExportProcess(Shop shop, Users user, Long shopId, Date startDate, Date endDate) {
        Set<ExportSalesReportRes> reportRes = new HashSet<>();

        logger.info("[START DATE {} END DATED {} ]", startDate, endDate);
        List<Sales> sales = salesRepository.findAllByShopIdAndFlagAndCreatedOnIsAfterAndCreatedOnIsBeforeOrderByIdDesc(shopId, AppConstants.ACTIVE_RECORD, startDate, endDate);

        sales.forEach(node -> {
            ExportSalesReportRes res = new ExportSalesReportRes();
            res.setAmount(node.getSoldAt());
            res.setTotal(node.getTotalAmount());
            res.setQuantity(node.getQuantity());
            res.setSaleDate(appFunctions.formatToHumandateTime(node.getCreatedOn()));
            Optional<Product> product = productRepository.findById(node.getProductId());
            if (product.isPresent()) {
                res.setProduct(product.get().getName());
                res.setCurrentStock(product.get().getStock());
                reportRes.add(res);
            }
        });
        logger.info("[FOUND SALES REPORT FOR SHOP {} SIZE {} SEND TO {} ] ", shopId, sales.size(), user.getEmail());
        sendEmailForSalesReport(shop, user, shop.getName() + "-SALE-REPORT" + new Date() + ".csv", reportRes);
        return new ResponseModel("00", "success", reportRes);
    }

    private void sendEmailForSalesReport(Shop shop, Users user, String fileName, Set<ExportSalesReportRes> records) {
        try {
            String destFolder = "UPLOADS" + "/report/sales/";
            if (!Files.exists(Paths.get(destFolder))) new File(destFolder).mkdirs();
            String fileUrl = destFolder + fileName;
            FileWriter fileWriter;
            fileWriter = new FileWriter(fileUrl);
            CsvMapper mapper = new CsvMapper();
            CsvSchema schema = mapper.schemaFor(ExportSalesReportRes.class).withHeader();
            mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
            ObjectWriter writer = mapper.writer(schema);
            writer.writeValue(fileWriter, records);
            String message = "Attached is " + shop.getName() + " sales report. \n For more info login our portal " + apv.httpBaseUrl;
            notifyByMailWithAttachment(user,
                    shop.getName().toUpperCase() + " Sales Report",
                    message,
                    fileUrl,
                    "application/vnd.ms-excel");
        } catch (IOException e) {
            logger.error(new Object() {
            }.getClass().getEnclosingMethod().getName(), e.getMessage());
        }
    }

    public void notifyByMailWithAttachment(Users user, String subject, String message, String attachmentUrl, String attachmentContentType) throws IOException {
        logger.info("Sending Email with Attachment");


    }

    /**
     * expense report
     */
    public ResponseModel expensesReport(Long shopId, Integer page, Integer size, HttpServletRequest httpServletRequest) {

        ResponseModel responseModel = shopService.verifyIfValidShop(shopId);
        if (!responseModel.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return responseModel;

        Shop shop = shopService.getShop(shopId);
        Users user = userDetailsService.getAuthicatedUser();
        dashboardService.saveAuditLogMobile("Accessing  expenses reports", shopId, httpServletRequest);
        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUser(shop, user);
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;
        if (!apiShopService.checkIfAllowed(ShopRights.PRIVILEGE_MY_EXPENSES, shopId))
            return new ResponseModel("01", "Sorry! You are not allowed to perform this operation.");
        logger.info(String.format("============ api expenses reports %s page %s size %s ", shopId, page, size));
        page = page != null ? page : 0;
        size = size != null ? size : 20;
        Pageable pageable = PageRequest.of(page, size);
        Page<Expenses> expenses = expensesRepository.findAllByShopIdOrderByIdDesc(shopId, pageable);
        List<ApiExpenseRes> expenseRes = new ArrayList<>();

        expenses.forEach(node -> {
            ApiExpenseRes apiExpense = new ApiExpenseRes();
            apiExpense.setId(node.getId());
            apiExpense.setName(node.getName());
            apiExpense.setAmount(node.getAmount());
            apiExpense.setAddedOn(appFunctions.formatDateTime(node.getCreatedOn()));
            apiExpense.setDescription(node.getDescription() != null ? node.getDescription() : "");
            if (user.getId() == node.getCreatedBy())
                apiExpense.setAddedBy("me");
            else
                apiExpense.setAddedBy(userRepository.findById(node.getCreatedBy()).get().getFirstname() != null ? userRepository.findById(node.getCreatedBy()).get().getFirstname() : "n/a");

            expenseRes.add(apiExpense);
        });
        return new ResponseModel("00", "success", expenseRes);
    }

    /**
     * debtors report
     */
    public ResponseModel getDebtorsReport(Long shopId, Integer page, Integer size, HttpServletRequest httpServletRequest) {

        ResponseModel responseModel = shopService.verifyIfValidShop(shopId);
        if (!responseModel.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return responseModel;

        Shop shop = shopService.getShop(shopId);
        Users user = userDetailsService.getAuthicatedUser();
        dashboardService.saveAuditLogMobile("Accessing  expenses reports", shopId, httpServletRequest);
        ResponseModel checkActiveShopUser = SystemComponent.checkActiveShopUser(shop, user);
        if (!checkActiveShopUser.getStatus().equalsIgnoreCase(AppConstants.SUCCESS_STATUS_CODE))
            return checkActiveShopUser;
        if (!apiShopService.checkIfAllowed(ShopRights.PRIVILEGE_DEBTS, shopId))
            return new ResponseModel("01", "Sorry! You are not allowed to perform this operation.");
        logger.info(String.format("============ api debtors reports %s page %s size %s ", shopId, page, size));
        page = page != null ? page : 0;
        size = size != null ? size : 20;
        Pageable pageable = PageRequest.of(page, size);
        Page<Credits> credits = creditsRepository.findAllByShopIdAndFlagOrderByIdDesc(shopId, AppConstants.ACTIVE_RECORD, pageable);
        List<ApiDebtorsRes> debtorsRes = new ArrayList<>();

        credits.forEach(node -> {
            ApiDebtorsRes debtor = new ApiDebtorsRes();
            Customer customer = customerRepository.findById(node.getCustomerId()).get();
            debtor.setId(node.getId());
            debtor.setName(customer.getName());
            debtor.setCustomerPhone(customer.getPhone());
            debtor.setAmount(node.getAmount());
            debtor.setBalance(node.getBalance());
            debtor.setStatus(node.getPaymentStatus());
            debtor.setDebtDate(appFunctions.formatDateTime(node.getCreatedOn()));
            if (user.getId() == node.getCreatedBy())
                debtor.setGivenBy("me");
            else
                debtor.setGivenBy(userRepository.findById(node.getCreatedBy()).get().getFirstname() != null ? userRepository.findById(node.getCreatedBy()).get().getFirstname() : "n/a");

            debtorsRes.add(debtor);
        });
        return new ResponseModel("00", "success", debtorsRes);
    }
}
