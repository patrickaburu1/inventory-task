package com.patrick.inventorymanagementtask.repositories.product;

import com.patrick.inventorymanagementtask.entities.products.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    Integer countAllByFlagAndShopId(String flag,long shoId);



    /**
     * get current stock
     * */
    @Query(nativeQuery = true, value = "select sum(buying_price*stock) from products where flag=:flag and shop_id=:shopId")
    BigDecimal getTotalStockWorth(@Param("flag") String flag, @Param("shopId") long shopId);


    /**
     * get product stock out
     *
     * @return List
     * */
    @Query(nativeQuery = true, value = "SELECT p.* FROM products p inner join products p1 on p1.id=p.id where p.re_order_level>=p1.stock and p.flag=:flag and p.shop_id=:shopId")
    List<Product> getStockOutProductsByFlag(@Param("flag") String flag, @Param("shopId") long shopId);

    /**
     * products List
     *
     * */
    List<Product> findAllByFlag(String flag);


    Optional<Product> findFirstByIdAndShopId(Long id,Long shopId);

    Page<Product> findAllByShopIdAndFlagOrderByNameAsc(Long shopId, String flag, Pageable pageable);

    Product findProductByCode(String code);

    Product findProductByCodeAndShopId(String code,Long  shopId);

    Optional<Product> findByIdAndShopId(Long id,Long shopId);

    Optional<Product> findFirstByShopId(Long shopId);
}
