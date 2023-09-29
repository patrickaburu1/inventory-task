package com.patrick.inventorymanagementtask.repositories;

import com.patrick.inventorymanagementtask.entities.products.Sales;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface SalesRepository extends CrudRepository<Sales, Long> {

    Page<Sales> findAllByShopIdAndFlagOrderByIdDesc(Long shopId,String flag, Pageable pageable);

    List<Sales> findAllByShopIdAndFlagOrderByIdDesc(Long shopId,String flag);

    List<Sales> findAllByShopIdAndFlagAndCreatedOnIsAfterAndCreatedOnIsBeforeOrderByIdDesc(Long shopId, String flag, Date startDate, Date endDate);


    List<Sales> findAllBySalesTransactionId(Long saleId);


    List<Sales> findAllBySalesTransactionIdAndFlag(Long saleId, String flag);

    @Query(nativeQuery = true, value = "select sum(total_amount) from sales where  shop_id=:shopId")
    BigDecimal totalSales(@Param("shopId") long shopId);

    @Query(nativeQuery = true, value = "select sum(total_amount) from sales where  shop_id=:shopId and flag=:flag and date(created_on) between :start and :end")
    BigDecimal totalSalesByDate(@Param("start") String start, @Param("end") String end, @Param("shopId") long shopId, @Param("flag") String flag);


    @Query(nativeQuery = true, value = "select sum(total_amount) from sales where payment_mode=:paymentMode and shop_id=:shopId and  date(created_on) between :start and :end")
    BigDecimal totalSalesByDateAndPaymentMode(@Param("paymentMode") long paymentMode,@Param("start") String start, @Param("end") String end, @Param("shopId") long shopId);


    @Query(nativeQuery = true, value = "select sum(total_amount) from sales where payment_mode=:paymentMode and shop_id=:shopId and created_by=:userId  and  date(created_on) between :start and :end")
    BigDecimal totalSalesByDateAndPaymentModeAndUserAndShop(@Param("paymentMode") long paymentMode, @Param("userId") Integer userId,@Param("start") String start, @Param("end") String end, @Param("shopId") long shopId);


    @Query(nativeQuery = true, value = "select sum(total_amount) from sales where  shop_id=:shopId and created_by=:userId  and  flag=:flag and   date(created_on) between :start and :end")
    BigDecimal totalSalesByDateAndShopAndUser(@Param("start") String start, @Param("end") String end, @Param("shopId") long shopId, @Param("userId") Integer userId,  @Param("flag") String flag);


    @Query(nativeQuery = true, value = "select sum(total_amount) from sales where MONTH(created_on)=MONTH(now()) and shop_id=:shopId")
    BigDecimal totalMonthSales(@Param("shopId") long shopId);

    @Query(nativeQuery = true, value = "select sum(total_amount) from sales where DATE (created_on)=date (now()) and shop_id=:shopId")
    BigDecimal todaysTotalSales(@Param("shopId") long shopId);

    @Query(nativeQuery = true, value = "select sum(total_profit) from sales where  shop_id=:shopId")
    BigDecimal totalProfit(@Param("shopId") long shopId);

    @Query(nativeQuery = true, value = "select sum(total_profit) from sales where shop_id=:shopId and flag=:flag and date(created_on) between :start and :end")
    BigDecimal profitByDate(@Param("start") String start, @Param("end") String end,@Param("shopId") long shopId, @Param("flag") String flag);

    @Query(nativeQuery = true, value = "select sum(total_profit) from sales where MONTH(created_on)=MONTH(now()) and shop_id=:shopId")
    BigDecimal totalMonthProfit(@Param("shopId") long shopId);

    @Query(nativeQuery = true, value = "select sum(total_profit) from sales where DATE (created_on)=date (now()) and shop_id=:shopId")
    BigDecimal todaysTotalProfit(@Param("shopId") long shopId);

    @Query(nativeQuery = true, value = "select count(id) from sales where MONTH(created_on)=MONTH(now()) and shop_id=:shopId")
    Integer totalProductSoldMonth(@Param("shopId") long shopId);

    @Query(nativeQuery = true, value = "select count(id) from sales where DATE (created_on)=DATE (now()) and shop_id=:shopId")
    Integer totalProductSoldToday(@Param("shopId") long shopId);

    @Query(nativeQuery = true, value = "select count(id) from sales where shop_id=:shopId and  date(created_on) between :start and :end")
    Integer totalProductSoldByDate(@Param("start") String start, @Param("end") String end,@Param("shopId") long shopId);


    @Query(nativeQuery = true, value = "select sum(total_amount) from sales where check_out_id=:checkOutId")
    BigDecimal totalByCheckOutId(@Param("checkOutId") long checkOutId);


    @Query(nativeQuery = true, value = "select sum(total_amount) from sales where payment_mode=:paymentMode and shop_id=:shopId and date(created_on) between :start and :end")
    BigDecimal totalSalesByDateAndByPamentMode(@Param("paymentMode") Long paymentMode,@Param("start") String start, @Param("end") String end,@Param("shopId") long shopId);



    @Query(nativeQuery = true, value = "select sum(total_amount) from sales where  sales_transaction_id=:transId")
    BigDecimal totalBySaleId( @Param("transId") long transId);



    @Query(nativeQuery = true ,value = "select  year(CREATED_ON) as year, monthname(CREATED_ON) as monthName, month(CREATED_ON) as month, sum(total_amount) as sales," +
            "  sum(total_amount) as profit from sales where shop_id=:shopId group by year(CREATED_ON),monthname(CREATED_ON), month(CREATED_ON)")
    List<Map<String,Object>> monthlySalesAndProfit(@Param("shopId") Long shopId);

    Boolean existsAllByShopId(Long shopId);

    Boolean existsAllByShopIdAndCreatedOnIsAfterAndCreatedOnIsBefore(Long shopId,Date startDate, Date endDate);
}
