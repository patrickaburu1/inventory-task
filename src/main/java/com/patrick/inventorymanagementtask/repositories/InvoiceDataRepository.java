package com.patrick.inventorymanagementtask.repositories;

import com.patrick.inventorymanagementtask.entities.InvoiceData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author patrick on 4/16/20
 * @project inventory
 */
@Repository
public interface InvoiceDataRepository extends CrudRepository<InvoiceData,Long> {

    List<InvoiceData> findAllByShopIdAndUserIdOrderByIdDesc(Long shopId,Integer userId);

    InvoiceData findFirstByShopIdAndUserIdAndProductId(Long shopId,Integer userId, Long productId);

    InvoiceData findFirstByIdAndShopIdAndUserId(Long id,Long shopId,Integer userId);

    @Query(nativeQuery = true, value = "select sum(total) from invoice_data where shop_id=:shopId and user_id=:userId")
    BigDecimal invoiceTotal(@Param("shopId") Long shopId, @Param("userId") Integer userId);
}
