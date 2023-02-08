package com.unishop.marketplace.service;

import com.unishop.marketplace.models.Product;
import com.unishop.marketplace.service.repository.ProductCatalogStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCatalogService {



    public ProductCatalogService(){

    }

    public List<Product> findAllProducts(){
        return ProductCatalogStore.findAllProducts();
    }


    public Product findProductById(String id){
        return ProductCatalogStore.findProductById(id);
    }
}
