package com.unishop.marketplace.service;

import com.unishop.marketplace.models.Order;
import com.unishop.marketplace.models.UserId;
import com.unishop.marketplace.service.repository.OrderHistoryStore;
import com.unishop.marketplace.service.repository.UserCartStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service to process order and get details
 */
@Service
public class OrderService {

    @Autowired
    private RulesProcessingService rulesProcessingService;

    @Autowired
    private UserRewardService rewardService; //= new UserRewardService();

    public int getOrderCount(UserId userId){
       return OrderHistoryStore.findOrderCount(userId);
    }

    public void processOrder(Order order){
        OrderHistoryStore.insertOrder(order.userId(), order);
        rulesProcessingService.applyRuleOnOrderComplete(order.userId());
        UserCartStore.clearCart(order.userId().value());
        if(order.appliedDiscountCoupon() != null) {
            rewardService.removeDiscount(order.userId(), order.appliedDiscountCoupon().discountCode()) ;
        }

    }


}
