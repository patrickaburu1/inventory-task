package com.patrick.inventorymanagementtask.repositories;

import com.patrick.inventorymanagementtask.entities.ShopPackagePaymentEntries;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author patrick on 5/1/20
 * @project inventory
 */
@Repository
public interface PaymentPlanEntriesRepository extends CrudRepository<ShopPackagePaymentEntries,Long> {
    ShopPackagePaymentEntries findFirstByReferenceNo(String referenceNo);
    ShopPackagePaymentEntries findFirstByRequestId(String requestId);

    Integer countAllByShopIdAndStatus(Long shopId, String status);

}
