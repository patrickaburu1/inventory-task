package com.patrick.inventorymanagementtask.repositories;

import com.patrick.inventorymanagementtask.entities.Expenses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * @author patrick on 4/23/20
 * @project inventory
 */
@Repository
public interface ExpensesRepository extends CrudRepository<Expenses,Long> {

    @Query(nativeQuery = true, value = "select sum(amount) from expenses where  shop_id=:shopId and created_by=:userId  and  date(created_on) between :start and :end")
    BigDecimal totalExpensesPerUser(@Param("shopId") long shopId, @Param("userId") Integer userId, @Param("start") String start, @Param("end") String end);


    @Query(nativeQuery = true, value = "select sum(amount) from expenses where  shop_id=:shopId and  date(created_on) between :start and :end")
    BigDecimal totalExpensesPerShop(@Param("shopId") long shopId, @Param("start") String start, @Param("end") String end);


    Page<Expenses> findAllByShopIdAndCreatedByOrderByIdDesc(Long shopId, Integer userId, Pageable pageable);

    Page<Expenses> findAllByShopIdOrderByIdDesc(Long shopId,  Pageable pageable);

}
