package com.patrick.inventorymanagementtask.repositories.transactions;

import com.patrick.inventorymanagementtask.entities.transactions.WalletEntries;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author patrick on 7/25/20
 * @project inventory
 */
@Repository
public interface WalletEntriesRepository extends CrudRepository<WalletEntries,Long> {

    WalletEntries getFirstByWalletIdAndFlagOrderByIdDesc(Long wallteId, String flag);

    Page<WalletEntries> findAllByWalletIdAndFlagOrderByIdDesc(Long walletId, String flag, Pageable  pageable);
}
