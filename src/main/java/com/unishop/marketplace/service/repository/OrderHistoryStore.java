package com.unishop.marketplace.service.repository;

import com.unishop.marketplace.models.Order;
import com.unishop.marketplace.models.UserId;

import java.util.*;

/**
 * In memory store for order history associated with user
 */
public class OrderHistoryStore {

    private static final Map<UserId, List<Order>> orderHistory = new HashMap<>();


    public static void insertOrder(UserId userId, Order order){
        orderHistory.computeIfAbsent(userId, k -> new ArrayList<Order>());
        orderHistory.get(userId).add(order);
    }


    public static int findOrderCount(UserId userId){
       return Optional.ofNullable(orderHistory.get(userId)).orElse(Collections.emptyList()).size();
    }
    

}
