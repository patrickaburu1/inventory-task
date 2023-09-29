package com.patrick.inventorymanagementtask.repositories.user;

import com.patrick.inventorymanagementtask.entities.UserVerify;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author patrick on 11/28/19
 * @project shop-pos
 */
@Repository
public interface UserVerifyRepository extends CrudRepository<UserVerify,Long> {

    UserVerify findByUserName(String userName);
    UserVerify findByCodeAndUserName(String code, String username);
}
