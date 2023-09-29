package com.patrick.inventorymanagementtask.repositories.product;

import com.patrick.inventorymanagementtask.entities.products.ClearedDebts;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * @author patrick on 8/17/19
 * @project  inventory
 */
@Repository
public interface ClearedDebtsRepository extends CrudRepository<ClearedDebts, Long> {

    ClearedDebts findTopByCheckOutId(long checkOutId);


    @Query(nativeQuery = true, value = "select sum(amount) from cleared_debts where shop_id=:shopId  and  date(created_on) between :start and :end")
    BigDecimal clearedShopsDebts(@Param("shopId") Long shopId, @Param("start") String start, @Param("end") String end);
}
