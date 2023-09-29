package com.patrick.inventorymanagementtask.repositories.user;

import com.patrick.inventorymanagementtask.entities.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author patrick on 6/19/19
 * @project pos
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    UserRole findDistinctTopByUserId(int userId);
}
