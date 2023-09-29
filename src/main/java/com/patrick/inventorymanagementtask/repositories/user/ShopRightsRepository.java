package com.patrick.inventorymanagementtask.repositories.user;

import com.patrick.inventorymanagementtask.entities.user.ShopRights;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author patrick on 8/3/19
 * @project shop-pos
 */
@Repository
public interface ShopRightsRepository extends CrudRepository<ShopRights, Long> {

    Optional<ShopRights> findFirstByCode(String code);

    List<ShopRights> findAllByDefaultRight(Boolean isDefaultRights);
}
