package com.patrick.inventorymanagementtask.repositories;

import com.patrick.inventorymanagementtask.entities.marketers.Marketers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author patrick on 7/25/20
 * @project inventory
 */
@Repository
public interface MarketersRepository extends CrudRepository<Marketers, Long> {

    Marketers findFirstByReferralCodeAndFlagAndStatus(String referralCode, String flag, String status);

    Marketers findFirstByUserId(Integer userId);

    Page<Marketers> findAllByFlagAndLeadByMarketerId(String flag, Long marketerLeadId, Pageable pageable);
}
