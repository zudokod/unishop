package com.unishop.marketplace.service.repository;

import com.unishop.marketplace.models.AvailableStock;
import com.unishop.marketplace.models.Product;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In memory store for products. Its predefined
 */
public class ProductCatalogStore {

    private static final Map<String, Product> productMap = new ConcurrentHashMap<>();

    static {
        productMap.put("1", new Product("1", "Surgical Mask", 10.00,  new AvailableStock(128)));
        productMap.put("2", new Product("2", "Surgical Gloves", 50.00, new AvailableStock(41)));
        productMap.put("3", new Product("3", "Unisex Hospital Gown", 100.00, new AvailableStock(68)));
        productMap.put("4", new Product("4", "Insulin Syringe", 40.00, new AvailableStock(117)));
    }

    public static void init(){}

    public static final List<Product> findAllProducts(){
        return productMap.values().stream().toList();
    }

    public static final Product findProductById(String id){
        return  productMap.get(id);
    }

    public static void addToProductStock(String key, int quantity ){
        Product product = productMap.get(key);
        int addon = 10;
        AvailableStock newStock = new AvailableStock(product.availableStock().quantity() + addon);
        productMap.replace(key, product.updateStock(newStock));
    }

    public static void reduceFromProductStock(String key, int quantity ){
        addToProductStock(key, -quantity);
    }


}
