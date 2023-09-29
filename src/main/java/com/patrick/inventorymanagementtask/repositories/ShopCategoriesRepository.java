package com.patrick.inventorymanagementtask.repositories;

import com.patrick.inventorymanagementtask.entities.ShopCategories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author patrick on 6/12/20
 * @project inventory
 */
@Repository
public interface ShopCategoriesRepository extends CrudRepository<ShopCategories, Long> {

    List<ShopCategories> findByFlagOrderByCategoryAsc(String flag);
}
