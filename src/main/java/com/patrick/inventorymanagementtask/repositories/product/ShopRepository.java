package com.patrick.inventorymanagementtask.repositories.product;

import com.patrick.inventorymanagementtask.entities.products.Shop;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author patrick on 11/21/19
 * @project  inventory
 */
@Repository
public interface ShopRepository extends CrudRepository<Shop,Long> {

    List<Shop> findAllByFlagAndPaymentStatus(String flag, String paymentStatus);

    Shop findTopByCreatedBy(int userNo);

    List<Shop> findAllByCreatedBy(int userId);

    @Query(nativeQuery = true, value = "SELECT * FROM shops where  flag=:flag and payment_status=:paymentStatus and DATEDIFF(payment_due_on,date(now()))>=:daysDueFrom and DATEDIFF(payment_due_on,date(now()))<=:daysToDue")
    List<Shop> getShopsAboutRenewal(@Param("flag") String flag, @Param("paymentStatus") String paymentStatus, @Param("daysDueFrom") Integer daysDueFrom,  @Param("daysToDue") Integer daysToDue);

    @Query(nativeQuery = true, value = "SELECT * FROM shops where flag=:flag and payment_status=:paymentStatus and DATEDIFF(payment_due_on,date(now()))=:daysToDue")
    List<Shop> getShopsRenewalDueExactDays(@Param("flag") String flag, @Param("paymentStatus") String paymentStatus, @Param("daysToDue") Integer daysToDue);


    Integer countAllByFlagAndCreatedByAndTrial(String flag, Integer createdBy, Boolean isTrail);

    Integer countAllByFlagAndMarketerIdAndPaymentStatus(String flag, Long marketerId, String paymentStatus);
}
