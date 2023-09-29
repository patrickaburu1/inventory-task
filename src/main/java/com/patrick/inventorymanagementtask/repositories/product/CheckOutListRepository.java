package com.patrick.inventorymanagementtask.repositories.product;

import com.patrick.inventorymanagementtask.entities.products.CheckOutList;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface CheckOutListRepository extends CrudRepository<CheckOutList, Long> {
    List<CheckOutList> findAllByCreatedByAndCheckOutIdAndFlag(int userId, long checkoutId, String flag);

    List<CheckOutList> findAllByCheckOutId( long checkoutId);

    Optional<CheckOutList> findAllByCreatedByAndCheckOutIdAndProductIdAndFlag(int userId, long checkoutId, long productId, String flag);

    @Query(nativeQuery = true, value = "SELECT sum(total)\n" +
            "FROM `checkout_list`\n" +
            "where  created_by=:userId  and check_out_id=:checkOutId and flag=:flag")
    BigDecimal getSumTopPay(@Param("userId") long userId, @Param("checkOutId") long checkOutId, @Param("flag") String flag);
}
