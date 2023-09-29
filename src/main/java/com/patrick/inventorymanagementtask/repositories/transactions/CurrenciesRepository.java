package com.patrick.inventorymanagementtask.repositories.transactions;

import com.patrick.inventorymanagementtask.entities.transactions.Currencies;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author patrick on 7/25/20
 * @project inventory
 */
@Repository
public interface CurrenciesRepository extends CrudRepository<Currencies,Long> {

    Currencies findFirstByCode(String code);

}
