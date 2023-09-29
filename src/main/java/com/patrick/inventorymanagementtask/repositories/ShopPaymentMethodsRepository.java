package com.patrick.inventorymanagementtask.repositories;

import com.patrick.inventorymanagementtask.entities.ShopPaymentMethods;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author patrick on 5/2/20
 * @project inventory
 */
@Repository
public interface ShopPaymentMethodsRepository extends CrudRepository<ShopPaymentMethods,Long> {

    List<ShopPaymentMethods> findAllByShopIdAndFlagAndActive(Long shopId, String flag, boolean active);

    ShopPaymentMethods findFirstByShopIdAndPaymentMethodId(Long shopId, Long paymentMethod);

    ShopPaymentMethods findFirstByShopIdAndPaymentMethodIdAndActive(Long shopId, Long paymentMethod, boolean active);
}
