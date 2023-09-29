package com.patrick.inventorymanagementtask.repositories;

import com.patrick.inventorymanagementtask.entities.marketers.MarketerWithdrawalsRequests;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author patrick on 7/27/20
 * @project inventory
 */
@Repository
public interface MarketerWithdrawalsRequestsRepo extends CrudRepository<MarketerWithdrawalsRequests, Long> {

    Integer countAllByStatusAndFlagAndMarketerId(String status, String flag, Long marketerId);

    MarketerWithdrawalsRequests findTopByTransactionId(Long transactionId);
}
