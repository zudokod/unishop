package com.unishop.marketplace.service.repository;

import com.unishop.marketplace.models.CartItem;
import com.unishop.marketplace.models.UserCart;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In memory store for cart associated with user
 */
public class UserCartStore {

    private static final Map<String, UserCart> userCartMap = new ConcurrentHashMap<>();


    public static void initializeCart(String userId){
        userCartMap.put(userId, UserCart.createEmptyCart(userId));
    }

    public static UserCart addItemToCart(String userId, CartItem item){
        return findCartByUserId(userId).updateCart(item);
    }

    public static void removeItemFromCart(String userId, CartItem item){
        findCartByUserId(userId).items().remove(item);
    }

    public static UserCart findCartByUserId(String userId){
        if (!userCartMap.containsKey(userId)) initializeCart(userId);
        return userCartMap.get(userId);
    }

    public static void clearCart(String userId){
        userCartMap.remove(userId);
    }



}
