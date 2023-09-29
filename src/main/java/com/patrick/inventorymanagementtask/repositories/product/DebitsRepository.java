package com.patrick.inventorymanagementtask.repositories.product;

import com.patrick.inventorymanagementtask.entities.products.Debits;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author patrick on 8/17/19
 * @project  inventory
 */
@Repository
public interface DebitsRepository extends CrudRepository<Debits,Long> {

    List<Debits> findAllByAmountIsGreaterThanAndShopIdOrderByIdDesc(BigDecimal amount,long shopId);

    @Query(nativeQuery = true, value = "select sum(amount) from debits where shop_id=:shopId and  date(created_on) between :start and :end")
    BigDecimal totalDeductions(@Param("start") String start, @Param("end") String end, @Param("shopId") long shopId);


    @Query(nativeQuery = true, value = "select sum(amount) from debits where debit=true and shop_id=:shopId and date(created_on) between :start and :end")
    BigDecimal totalDebits(@Param("start") String start, @Param("end") String end, @Param("shopId") long shopId);



    @Query(nativeQuery = true, value = "select sum(amount) from debits where expense=true and shop_id=:shopId and date(created_on) between :start and :end")
    BigDecimal totalExpenses(@Param("start") String start, @Param("end") String end, @Param("shopId") long shopId);


}
