package com.patrick.inventorymanagementtask.repositories.user;


import com.patrick.inventorymanagementtask.entities.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository("roleRepository")
public interface RoleRespository extends JpaRepository<Role, Integer> {

    Role findByRole(String role);
}
