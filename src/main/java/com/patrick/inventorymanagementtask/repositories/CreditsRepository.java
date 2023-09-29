package com.patrick.inventorymanagementtask.repositories;

import com.patrick.inventorymanagementtask.entities.Credits;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public interface CreditsRepository extends CrudRepository<Credits,Long> {

    @Query(nativeQuery = true, value = "select sum(balance) from credits where  shop_id=:shopId and  flag=:flag and  date(created_on) between :start and :end and (payment_status=:pending or payment_status=:partially )")
    BigDecimal totalDebtsOutStandingDebts(@Param("shopId") long shopId,  @Param("flag") String flag, @Param("start") String start, @Param("end") String end, @Param("pending") String pending, @Param("partially") String partially);

    @Query(nativeQuery = true, value = "select sum(amount) from credits where  shop_id=:shopId and  date(created_on) between :start and :end and flag=:flag ")
    BigDecimal totalDebts(@Param("shopId") long shopId, @Param("start") String start, @Param("end") String end, @Param("flag") String flag);

    Page<Credits> findAllByShopIdAndFlagOrderByIdDesc(Long shopId, String flag, Pageable pageable);

    Credits findFirstBySaleTransactionId(Long transactionId);
}
