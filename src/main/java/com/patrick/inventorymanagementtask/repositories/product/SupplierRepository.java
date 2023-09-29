package com.patrick.inventorymanagementtask.repositories.product;

import com.patrick.inventorymanagementtask.entities.products.Suppliers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends CrudRepository<Suppliers,Integer > {

    List<Suppliers> getAllBy();
    List<Suppliers> getAllByShopId(long shopId);

    Suppliers findFirstByNameAndShopId(String name, Long shopId);
    Suppliers findFirstByPhoneAndShopId(String name, Long shopId);
    Suppliers findFirstByIdAndShopId(Integer id, Long shopId);

    Page<Suppliers> findAllByShopIdOrderByNameAsc(Long shopId, Pageable pageable);
}
