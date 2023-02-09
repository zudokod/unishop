package com.unishop.marketplace;

import com.unishop.marketplace.models.Product;
import com.unishop.marketplace.service.ProductCatalogService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class ProductCatalogConfiguration {

    @Bean
    @Primary
    ProductCatalogService productCatalogService() {
        //return new ProductCatalogService();
        return Mockito.mock(ProductCatalogService.class);

    }
}
