package com.patrick.inventorymanagementtask.repositories;

import com.patrick.inventorymanagementtask.entities.ShopPackagePlans;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author patrick on 6/5/20
 * @project inventory
 */
@Repository
public interface ShopPackagePlansRepository extends CrudRepository<ShopPackagePlans,Long> {

    List<ShopPackagePlans> findAllByPackageIdAndFlag(Long packageId, String flag);
}
