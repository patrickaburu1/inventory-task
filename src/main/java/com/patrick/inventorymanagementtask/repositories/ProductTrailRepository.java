package com.patrick.inventorymanagementtask.repositories;

import com.patrick.inventorymanagementtask.entities.ProductTrail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author patrick on 5/1/20
 * @project inventory
 */
@Repository
public interface ProductTrailRepository extends CrudRepository<ProductTrail,Long> {
}
