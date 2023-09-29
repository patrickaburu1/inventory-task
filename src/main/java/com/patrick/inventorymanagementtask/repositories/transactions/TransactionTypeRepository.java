package com.patrick.inventorymanagementtask.repositories.transactions;

import com.patrick.inventorymanagementtask.entities.transactions.TransactionTypes;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author patrick on 7/25/20
 * @project inventory
 */
@Repository
public interface TransactionTypeRepository extends CrudRepository<TransactionTypes,Long> {

    TransactionTypes findFirstByCode(String code);
}
