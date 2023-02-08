package com.unishop.marketplace.service;

import com.unishop.marketplace.models.CartItem;
import com.unishop.marketplace.models.UserCart;
import com.unishop.marketplace.service.repository.UserCartStore;
import org.springframework.stereotype.Service;

@Service
public class UserCartService {

    public  UserCart addItemToCart(String userId, CartItem item){
       return UserCartStore.addItemToCart(userId, item);
    }

    public  void removeItemFromCart(String userId, CartItem item){
        UserCartStore.removeItemFromCart(userId, item);
    }

    public UserCart fetchCart(String userId){
        return UserCartStore.findCartByUserId(userId);
    }

}
