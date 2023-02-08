package com.unishop.marketplace.service;

import com.unishop.marketplace.models.UserCart;
import org.springframework.stereotype.Service;

@Service
public class CheckoutService {

    private OrderService orderService;

    public void checkout(UserCart userCart){

           // apply coupons
           // apply shipping

    }

    public void getDiscountCode(String userId){

        // apply coupons
        // apply shipping
        orderService.findOrderCount(userId);

    }

}
