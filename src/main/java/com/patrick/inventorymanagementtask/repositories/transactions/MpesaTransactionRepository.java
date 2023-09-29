package com.patrick.inventorymanagementtask.repositories.transactions;

import com.patrick.inventorymanagementtask.entities.transactions.MpesaTransactions;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author patrick on 7/25/20
 * @project inventory
 */
@Repository
public interface MpesaTransactionRepository extends CrudRepository<MpesaTransactions,Long> {

    MpesaTransactions findFirstByCheckoutId(String checkoutId);

    MpesaTransactions findFirstByMpesaRef(String mpesaRef);

}
