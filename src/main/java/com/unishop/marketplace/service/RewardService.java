package com.unishop.marketplace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RewardService {

    @Autowired
    OrderService orderService;

    public void getRewards(String userId) {
        orderService.findOrderCount(userId);
    }
}
