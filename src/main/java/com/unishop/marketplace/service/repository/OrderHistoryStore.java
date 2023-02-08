package com.unishop.marketplace.service.repository;

import com.unishop.marketplace.models.Order;
import com.unishop.marketplace.models.Product;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class OrderHistoryStore {

    private static final Map<String, List<Order>> orderHistory = new HashMap<>();


    public static void insertOrder(String userId, List<Order> orders){
        orderHistory.put(userId, orders);
    }


    public static int findOrderCount(String userId){
       return Optional.of(orderHistory.get(userId)).orElse(Collections.emptyList()).size();
    }

}
