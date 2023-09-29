package com.patrick.inventorymanagementtask.repositories;

import com.patrick.inventorymanagementtask.entities.configs.CreditPayments;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * @author patrick on 5/23/20
 * @project inventory
 */
@Repository
public interface CreditPaymentsRepository extends CrudRepository<CreditPayments,Long> {

    @Query(nativeQuery = true, value = "select sum(amount) from credit_payments where  shop_id=:shopId and created_by=:userId  and  date(created_on) between :start and :end")
    BigDecimal totalUserDebtsCollected(@Param("shopId") long shopId, @Param("userId") Integer userId, @Param("start") String start, @Param("end") String end);

    @Query(nativeQuery = true, value = "select sum(amount) from credit_payments where  shop_id=:shopId and  date(created_on) between :start and :end")
    BigDecimal totalDebtsCollected(@Param("shopId") long shopId, @Param("start") String start, @Param("end") String end);

    CreditPayments findFirstByCreditId(Long creditId);
}
