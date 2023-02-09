package com.unishop.marketplace.service;

import com.unishop.marketplace.models.Product;
import com.unishop.marketplace.service.repository.ProductCatalogStore;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for product APIs
 */

@Service
public class ProductCatalogService {

    /**
     * List all products
     * @return
     */
    public List<Product> findAllProducts(){
        return ProductCatalogStore.findAllProducts();
    }


    /**
     * Find prodcut by ID
     * @param id
     * @return
     */
    public Product findProductById(String id){
        return ProductCatalogStore.findProductById(id);
    }
}
