package com.patrick.inventorymanagementtask.repositories;

import com.patrick.inventorymanagementtask.entities.PhoneVerification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author patrick on 3/23/20
 * @project  inventory
 */
@Repository
public interface PhoneVerificationRepository extends CrudRepository<PhoneVerification,Long> {
    PhoneVerification findByPhoneNumber(String phone);
    PhoneVerification findByPhoneNumberAndCode(String phone, String code);
}
