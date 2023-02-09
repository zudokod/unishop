package com.unishop.marketplace.rewards.discount;

import com.unishop.marketplace.models.Order;
import com.unishop.marketplace.rewards.rules.Rule;
import org.springframework.stereotype.Service;

/***
 * A service abstraction to support discount at a time
 */

@Service
public class OrderDiscountService {
    private OrderDiscountMethod strategy;

    public void setDiscountMethod(OrderDiscountMethod strategy){
        this.strategy = strategy;
    }

    public Order process(Order order, Rule.Discount discount){
        return this.strategy.process(order, discount);
    }
}
