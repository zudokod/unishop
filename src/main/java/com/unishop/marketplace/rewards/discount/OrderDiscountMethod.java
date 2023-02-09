package com.unishop.marketplace.rewards.discount;

import com.unishop.marketplace.models.Order;
import com.unishop.marketplace.rewards.rules.Rule;

/***
 * Strategy interface for different type of discounts
 */
public interface OrderDiscountMethod {

    Order process(Order order, Rule.Discount discount);
}
