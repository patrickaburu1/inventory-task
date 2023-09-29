package com.patrick.inventorymanagementtask.repositories.product;

import com.patrick.inventorymanagementtask.entities.products.PaymentMethods;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author patrick on 8/9/19
 * @project  inventory
 */
@Repository
public interface PaymentMethodsRepository extends CrudRepository<PaymentMethods,Long> {

    Optional<PaymentMethods> findFirstByIdAndIsActive(long id, boolean isActive);

    List<PaymentMethods> findAllByIsActive(boolean isActive);


    PaymentMethods findTopByCode(String name);

    List<PaymentMethods> findAllByIsActive(Boolean active);
}
