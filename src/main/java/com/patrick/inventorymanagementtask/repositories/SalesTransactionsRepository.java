package com.patrick.inventorymanagementtask.repositories;

import com.patrick.inventorymanagementtask.entities.SalesTransactions;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author patrick on 4/20/20
 * @project inventory
 */
@Repository
public interface SalesTransactionsRepository extends CrudRepository<SalesTransactions, Long> {

}
