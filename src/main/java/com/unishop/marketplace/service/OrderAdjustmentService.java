package com.unishop.marketplace.service;

import com.unishop.marketplace.models.Order;
import com.unishop.marketplace.models.UserCart;
import com.unishop.marketplace.rewards.discount.OrderDiscountService;
import com.unishop.marketplace.rewards.discount.PercentageReducer;
import com.unishop.marketplace.rewards.rules.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Handles the supported adjustment operations for reward for order. Currently only support discount
 */
@Service
public class OrderAdjustmentService {

    @Autowired
    private UserRewardService rewardService;

    @Autowired
    private OrderDiscountService orderDiscountService;

    @Autowired
    private PercentageReducer percentageReducer;

    /***
     * Allows various methods of discount processing.
     * @param order
     * @param userCart
     * @return
     */
    public Order calculateOrder(Order order, UserCart userCart){

        // note that we are supporting only discount at time
        if(userCart.rewardIds().size() > 0) {
            Rule.Discount discount = this.rewardService.getDiscountByCode(order.userId(), userCart.rewardIds().get(0));
            if(discount == null) return  order;
            // Switch is here to demonstrate various options possible.
            switch (discount.discountType()) {
                // this is applicable only if we are using one type of discount, say percentage or reduce fixed amount
                case ORDER_AMOUNT_PERCENT -> orderDiscountService.setDiscountMethod(percentageReducer);
            }

            return orderDiscountService.process(order, discount);
        }
        return order;
    }
}
