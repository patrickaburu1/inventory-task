package com.patrick.inventorymanagementtask.repositories.product;

import com.patrick.inventorymanagementtask.entities.products.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Customer findDistinctByPhoneAndShopId(String phone,long shopId);
    List<Customer> findAllByShopId(long shopId);

    Customer findTopByIdAndShopId(Long id,Long shopId);

    Page<Customer> findAllByShopIdOrderByNameAsc(Long shopId, Pageable pageable);

    Customer findFirstByPhoneAndShopId(String phone,Long shopId);

    List<Customer>  findAllByIdInAndShopId(List<Long> ids, Long shopId);

    List<Customer>  findAllByIdIn(List<Long> id);

}
