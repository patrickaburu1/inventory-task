package com.patrick.inventorymanagementtask.repositories.product;

import com.patrick.inventorymanagementtask.entities.products.CheckOut;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CheckOutRepository extends CrudRepository<CheckOut, Long> {

    CheckOut findDistinctTopByCreatedByAndFlag(int createdBy, String flag);

    CheckOut findDistinctTopByCreatedByAndFlagAndShopId(int createdBy, String flag,long shopId);

    @Query(nativeQuery = true, value = "SELECT c.* FROM sales s left join checkout c on c.id =s.check_out_id where s.payment_mode=:paymentMode and shop_id=:shopId group by c.id")
    List<CheckOut> getCheckOutsByPaymentMode(@Param("paymentMode") long paymentMode, @Param("shopId") long shopId);

    List<CheckOut> findAllByCreatedOnIsBefore(Date date);
}
