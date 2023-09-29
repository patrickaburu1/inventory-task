package com.patrick.inventorymanagementtask.repositories.transactions;

import com.patrick.inventorymanagementtask.entities.transactions.WalletTypes;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author patrick on 7/25/20
 * @project inventory
 */
@Repository
public interface WalletTypeRepository extends CrudRepository<WalletTypes, Long> {
    WalletTypes findFirstByCode(String code);
}
