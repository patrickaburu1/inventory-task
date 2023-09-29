package com.patrick.inventorymanagementtask.repositories.user;


import com.patrick.inventorymanagementtask.entities.user.ShopEmployeeGroups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ShopEmployeeGroupsRepository extends JpaRepository<ShopEmployeeGroups, Long> {

    ShopEmployeeGroups findByRoleAndShopId(String role, long shopId);

    Optional<ShopEmployeeGroups> findByIdAndShopId(long id, long shopId);

    List<ShopEmployeeGroups> findByShopIdAndFlag(long shopId, String flag);
}
