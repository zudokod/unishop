package com.unishop.marketplace.rewards.discount;

import com.unishop.marketplace.models.Order;
import com.unishop.marketplace.rewards.rules.Rule;
import org.springframework.stereotype.Component;

/***
 * To support order amount reduction by percentage
 */

@Component
public class PercentageReducer implements OrderDiscountMethod {
    @Override
    public Order process(Order order, Rule.Discount discount) {

        final double originalAmount =  order.orderItems().stream().mapToDouble(Order.OrderItem::price).sum();
        final int itemCount = order.orderItems().stream().mapToInt(Order.OrderItem::quantity).sum();
        int discountPercentage = discount.percentage();
        final double discountAmount = originalAmount * discountPercentage / 100.0;
        final double finalAmount = originalAmount - discountAmount;
        return new Order(order.userId(), order.orderItems(), itemCount, originalAmount, discountAmount, finalAmount, discount);
    }
}
