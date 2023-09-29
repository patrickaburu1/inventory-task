package com.patrick.inventorymanagementtask.repositories.user;

import com.patrick.inventorymanagementtask.entities.user.UserTypes;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author patrick on 5/28/20
 * @project inventory
 */
@Repository
public interface UserTypeRepository extends CrudRepository<UserTypes,Long> {

    UserTypes findFirstByName(String name);
}
