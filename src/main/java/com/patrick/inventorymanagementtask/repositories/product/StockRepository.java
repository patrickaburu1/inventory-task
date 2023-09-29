package com.patrick.inventorymanagementtask.repositories.product;

import com.patrick.inventorymanagementtask.entities.products.Stock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface StockRepository extends CrudRepository<Stock, Long> {

    Stock findFirstByProductIdOrderByIdDesc(Long productId);


    @Query(nativeQuery = true, value = "select sum(buying_price*quantity) from stock where  shop_id=:shopId and  date(created_on) between :start and :end")
    BigDecimal totalPurchasesByDate(@Param("shopId") long shopId, @Param("start") String start, @Param("end") String end);

}
