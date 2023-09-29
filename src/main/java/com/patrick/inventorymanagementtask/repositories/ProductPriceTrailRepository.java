package com.patrick.inventorymanagementtask.repositories;

import com.patrick.inventorymanagementtask.entities.ProductPriceTrail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author patrick on 5/1/20
 * @project inventory
 */
@Repository
public interface ProductPriceTrailRepository extends CrudRepository<ProductPriceTrail,Long> {
}
