package com.patrick.inventorymanagementtask.repositories.user;


import com.patrick.inventorymanagementtask.entities.user.ShopEmployeesGroupRights;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author patrick on 8/7/19
 * @project  inventory
 */
@Repository
public interface ShopEmployeesGroupRightsRepository extends CrudRepository<ShopEmployeesGroupRights, Long> {

    Optional<ShopEmployeesGroupRights> findAllByRoleIdAndPrivilegeIdAndShopId(long roleId, long privilegeId, long shopId);

}
