package com.patrick.inventorymanagementtask.repositories;

import com.patrick.inventorymanagementtask.entities.SalesTransactionPayments;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author patrick on 5/2/20
 * @project inventory
 */
@Repository
public interface SalesTransactionPaymentsRepository extends CrudRepository<SalesTransactionPayments,Long> {
    SalesTransactionPayments findTopBySalesTransactionId(Long salesTransactionId);

    List<SalesTransactionPayments> findAllBySalesTransactionId(Long salesTransactionId);

    @Query(nativeQuery = true, value = "select sum(amount) from sales_transaction_payments where payment_method=:paymentMethod and flag=:flag and created_by=:createdBy and shop_id=:shopId  and  date(created_on) between :start and :end")
    BigDecimal totalSalesByDateAndPaymentModeAndUser(@Param("paymentMethod") long paymentMethod, @Param("shopId") Long shopId, @Param("flag") String flag, @Param("createdBy") Integer createdBy, @Param("start") String start, @Param("end") String end);


    @Query(nativeQuery = true, value = "select sum(amount) from sales_transaction_payments where created_by=:createdBy  and shop_id=:shopId  and  date(created_on) between :start and :end")
    BigDecimal totalSalesByDateAndPaymentModeAndUserIdAndShopId( @Param("createdBy") Integer createdBy, @Param("shopId") Long shopId, @Param("start") String start, @Param("end") String end);


    @Query(nativeQuery = true, value = "select sum(amount) from sales_transaction_payments where payment_method=:paymentMethod  and flag=:flag and shop_id=:shopId  and  date(created_on) between :start and :end")
    BigDecimal totalSalesByDateAndPaymentMethodAndShop(@Param("paymentMethod") long paymentMethod, @Param("shopId") Long shopId,  @Param("flag") String flag,  @Param("start") String start, @Param("end") String end);


    @Query(nativeQuery = true, value = "select sum(amount) from sales_transaction_payments where  shop_id=:shopId  and  date(created_on) between :start and :end")
    BigDecimal totalSalesByDateAndPaymentModeAndShopId(  @Param("shopId") Long shopId, @Param("start") String start, @Param("end") String end);

}
