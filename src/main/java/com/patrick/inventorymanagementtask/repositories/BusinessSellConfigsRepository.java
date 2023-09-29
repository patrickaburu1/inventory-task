package com.patrick.inventorymanagementtask.repositories;

import com.patrick.inventorymanagementtask.entities.configs.BusinessSellConfigs;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author patrick on 11/10/21
 * @project inventory
 */
@Repository
public interface BusinessSellConfigsRepository  extends CrudRepository<BusinessSellConfigs,Long> {

    BusinessSellConfigs  findFirstByShopId(Long shopId);

}
