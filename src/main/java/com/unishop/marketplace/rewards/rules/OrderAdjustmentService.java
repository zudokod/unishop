package com.unishop.marketplace.rewards.rules;

import com.unishop.marketplace.models.Order;
import com.unishop.marketplace.models.UserCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderAdjustmentService {

    @Autowired
    private UserRewardService rewardService;

    public Order calculateOrder(Order order, UserCart userCart){

        if(userCart.rewardIds().size() > 0) {
            Rule.Discount discount = this.rewardService.getDiscountByCode(order.userId(), userCart.rewardIds().get(0));
            final double originalAmount =  order.orderItems().stream().mapToDouble(Order.OrderItem::price).sum();
            final int itemCount = order.orderItems().stream().mapToInt(Order.OrderItem::quantity).sum();
            int discountPercentage = discount.percentage();
            final double discountAmount = originalAmount * discountPercentage / 100.0;
            final double finalAmount = originalAmount - discountAmount;
            return new Order(order.userId(), order.orderItems(), itemCount, originalAmount, discountAmount, finalAmount, discount);
        }

        return order;

    }
}
