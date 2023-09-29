package com.patrick.inventorymanagementtask.utils;

import com.patrick.inventorymanagementtask.entities.ProductPriceTrail;
import com.patrick.inventorymanagementtask.entities.ProductTrail;
import com.patrick.inventorymanagementtask.entities.products.Product;
import com.patrick.inventorymanagementtask.entities.products.Shop;
import com.patrick.inventorymanagementtask.entities.user.Users;
import com.patrick.inventorymanagementtask.properties.ApplicationMessages;
import com.patrick.inventorymanagementtask.repositories.ProductPriceTrailRepository;
import com.patrick.inventorymanagementtask.repositories.ProductTrailRepository;
import com.patrick.inventorymanagementtask.repositories.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

/**
 * @author patrick on 5/1/20
 * @project myduka-pos
 */
@Component
public class ProductComponent {

    private static ProductComponent instance;

    @Autowired
    private ProductTrailRepository productTrailRepository;
    @Autowired
    private ProductPriceTrailRepository productPriceTrailRepository;
    @Autowired
    private ProductRepository productRepository;

    public static void trackProductTrailDeduct(Product product, Shop shop, Users user, Integer quantity, String type, String description) {
        ProductTrail productTrail = new ProductTrail();
        productTrail.setProductId(product.getId());
        productTrail.setType(type);
        productTrail.setDescription(description);
        productTrail.setFlag(AppConstants.ACTIVE_RECORD);
        productTrail.setQuantity(quantity);
        productTrail.setStockBefore(product.getStock());
        productTrail.setStockAfter(product.getStock() - quantity);
        productTrail.setShopId(shop.getId());
        productTrail.setCreatedBy(user.getId());

        instance.productTrailRepository.save(productTrail);

    }

    public static void trackProductTrailDeductWeb(Product product, Shop shop, Users user, Integer quantity, String type, String description) {
        ProductTrail productTrail = new ProductTrail();
        productTrail.setProductId(product.getId());
        productTrail.setType(type);
        productTrail.setDescription(description);
        productTrail.setFlag(AppConstants.ACTIVE_RECORD);
        productTrail.setQuantity(quantity);
        productTrail.setStockBefore(product.getStock() + quantity);
        productTrail.setStockAfter((product.getStock() + quantity) - quantity);
        productTrail.setShopId(shop.getId());
        productTrail.setCreatedBy(user.getId());

        instance.productTrailRepository.save(productTrail);

    }

    public static void trackProductTrailAdd(Product product, Shop shop, Users user, Integer quantity, String type, String description) {
        ProductTrail productTrail = new ProductTrail();
        productTrail.setProductId(product.getId());
        productTrail.setType(type);
        productTrail.setDescription(description);
        productTrail.setFlag(AppConstants.ACTIVE_RECORD);
        productTrail.setQuantity(quantity);
        productTrail.setStockBefore(product.getStock());
        productTrail.setStockAfter(product.getStock() + quantity);
        productTrail.setShopId(shop.getId());
        productTrail.setCreatedBy(user.getId());
        instance.productTrailRepository.save(productTrail);

    }

    public static void trackPriceProductPriceChange(Product product, Shop shop, Users user, BigDecimal newBp, BigDecimal newSp, BigDecimal newMinSp, String type,
                                                    String description) {

        //check if price have change
        if (product.getBuyingPrice().compareTo(newBp) != 0 || product.getSellingPrice().compareTo(newSp) != 0 || product.getMinSellingPrice().compareTo(newMinSp) != 0) {
            ProductPriceTrail productPriceTrail = new ProductPriceTrail();
            productPriceTrail.setShopId(shop.getId());
            productPriceTrail.setProductId(product.getId());
            productPriceTrail.setCreatedBy(user.getId());
            productPriceTrail.setBuyingPrice(product.getBuyingPrice());
            productPriceTrail.setSellingPrice(product.getSellingPrice());
            productPriceTrail.setMinSellingPrice(product.getMinSellingPrice());
            productPriceTrail.setNewBuyingPrice(newBp);
            productPriceTrail.setNewSellingPrice(newSp);
            productPriceTrail.setNewMinSellingPrice(newMinSp);
            productPriceTrail.setType(type);
            productPriceTrail.setDescription(description);
            instance.productPriceTrailRepository.save(productPriceTrail);
        }
    }

    public static ResponseModel validateProductPrices(BigDecimal buyingPrice, BigDecimal sellingPrice, BigDecimal minSellingPrice) {
        ResponseModel response = new ResponseModel();
        response.setStatus("01");
        if (sellingPrice.compareTo(buyingPrice) < 0)
            response.setMessage("Failed, Product  Buying Price cannot be greater than selling price.");
        else if (sellingPrice.compareTo(buyingPrice) == 0)
            response.setMessage("Failed, Product  Buying Price cannot be equal to selling price.");
        else if (minSellingPrice.compareTo(buyingPrice) < 0)
            response.setMessage("Failed, Product min selling price cannot be less than  buying price.");
        else if (sellingPrice.compareTo(minSellingPrice) < 0)
            response.setMessage("Failed, Product min selling price cannot be greater than selling price.");
        else {
            response.setStatus("00");
            response.setMessage("success product verified");
        }
        return response;
    }

    public static ResponseModel validateProductPricesWithProductName(Product product, BigDecimal buyingPrice, BigDecimal sellingPrice, BigDecimal minSellingPrice) {
        ResponseModel response = new ResponseModel();
        response.setStatus("01");
        if (sellingPrice.compareTo(buyingPrice) < 0)
            response.setMessage(String.format(ApplicationMessages.get("response.product.low.sp.multiple"), product.getName()));
        else if (sellingPrice.compareTo(buyingPrice) == 0)
            response.setMessage(String.format(ApplicationMessages.get("response.product.sp.same.bp.multiple"), product.getName()));
        else if (minSellingPrice.compareTo(buyingPrice) < 0)
            response.setMessage(String.format(ApplicationMessages.get("response.product.low.minsp.multiple"), product.getName()));
        else if (sellingPrice.compareTo(minSellingPrice) < 0)
            response.setMessage(String.format(ApplicationMessages.get("response.product.high.minsp.multiple"), product.getName()));
        else {
            response.setStatus("00");
            response.setMessage("success");
        }
        return response;
    }

    public static void autoUpdateProductsPrices(Product product, Shop shop, Users user, BigDecimal buyingPrice, BigDecimal sellingPrice, BigDecimal minSellingPrice) {
        if (product.getBuyingPrice().compareTo(buyingPrice) < 0 || product.getSellingPrice().compareTo(sellingPrice) < 0 ||
                product.getMinSellingPrice().compareTo(minSellingPrice) < 0) {

            ProductComponent.trackPriceProductPriceChange(product, shop, user, buyingPrice, sellingPrice, minSellingPrice, "AutoUpdate", "Prices updated while adding stock (prices hiked) ");

            product.setSellingPrice(sellingPrice)
                    .setMinSellingPrice(minSellingPrice)
                    .setBuyingPrice(buyingPrice);

            instance.productRepository.save(product);


        }
    }

    @PostConstruct
    public void registerInstance() {
        instance = this;
    }


}
