package com.patrick.inventorymanagementtask.repositories.user;

import com.patrick.inventorymanagementtask.entities.user.ShopEmployees;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author patrick on 6/19/19
 * @project pos
 */
@Repository
public interface ShopEmployeesRepository extends CrudRepository<ShopEmployees, Long> {
    ShopEmployees findDistinctTopByUserIdAndShopId(int userId, long shopId);

    ShopEmployees findTopByIdAndShopId(Long id, long shopId);

    List<ShopEmployees> findAllByShopId(long shopId);

    List<ShopEmployees> findAllByUserIdAndActive(int userId, String active);

    ShopEmployees findFirstByUserIdAndDefaultShop(Integer userId, Boolean isDefault);

    ShopEmployees findFirstByUserIdAndDefaultShopAndActive(Integer userId, Boolean isDefault, String status);

    Integer countAllByShopIdAndActive(Long shopId,String status);
}
