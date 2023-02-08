package com.unishop.marketplace.service;

import com.unishop.marketplace.models.UserCart;
import com.unishop.marketplace.service.repository.OrderHistoryStore;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    public void createOrder(UserCart cart){
       // Order order = new Order()
    }

    public void findOrderCount(String userId){
        // Order order = new Order()
        OrderHistoryStore.findOrderCount(userId);
    }
}
