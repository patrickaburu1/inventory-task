package com.patrick.inventorymanagementtask.repositories.product;

import com.patrick.inventorymanagementtask.entities.products.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {

    List<Category> findAllByShopId(long shopId);

    Category findTopByNameAndShopId(String name,long shopId);

    Category findTopByIdAndShopId(Integer id, Long shopId);
}
