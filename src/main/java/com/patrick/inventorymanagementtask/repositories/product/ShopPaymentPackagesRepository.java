package com.patrick.inventorymanagementtask.repositories.product;

import com.patrick.inventorymanagementtask.entities.ShopPaymentPackages;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author patrick on 11/27/19
 * @project  inventory
 */
@Repository
public interface ShopPaymentPackagesRepository extends CrudRepository<ShopPaymentPackages,Long> {

    List<ShopPaymentPackages> findAllByFlag(String flag);

    List<ShopPaymentPackages> findAllByFlagAndTrial(String flag, Boolean isTrial);

    ShopPaymentPackages findFirstByTrial(Boolean trial);

    Optional<ShopPaymentPackages> findTopByTrialOrderByIdDesc(Boolean trial);

    ShopPaymentPackages findFirstByName(String name);

    List<ShopPaymentPackages> findAllByFlagOrderByDisplayOrderAsc(String flag);
    List<ShopPaymentPackages> findAllByFlagAndTrialOrderByDisplayOrderAsc(String flag, Boolean isTrail);

}
