package com.unishop.marketplace.service;

import com.unishop.marketplace.models.CartItem;
import com.unishop.marketplace.models.Product;
import com.unishop.marketplace.models.UserCart;
import com.unishop.marketplace.service.repository.UserCartStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCartService {

    @Autowired
    private ProductCatalogService catalogService;

    public  UserCart addItemToCart(String userId, String productId, int quantity){
        Product product = catalogService.findProductById(productId);
        CartItem item = new CartItem(product, quantity);
        return UserCartStore.addItemToCart(userId, item);
    }

    public  void removeItemFromCart(String userId, CartItem item){
        UserCartStore.removeItemFromCart(userId, item);
    }

    public UserCart fetchCart(String userId){
        return UserCartStore.findCartByUserId(userId);
    }

}
