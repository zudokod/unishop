package com.unishop.marketplace.controllers;

import com.unishop.marketplace.models.CartItem;
import com.unishop.marketplace.models.Order;
import com.unishop.marketplace.models.Product;
import com.unishop.marketplace.models.UserCart;
import com.unishop.marketplace.models.UserId;
import com.unishop.marketplace.rewards.rules.Rule;
import com.unishop.marketplace.service.UserRewardService;
import com.unishop.marketplace.service.CheckoutService;
import com.unishop.marketplace.service.OrderService;
import com.unishop.marketplace.service.ProductCatalogService;
import com.unishop.marketplace.service.UserCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Handles Checkout APIs
 */
@RestController
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private UserCartService userCartService;

    @Autowired
    private ProductCatalogService catalogService;

    @Autowired
    private UserRewardService rewardService;

    @Autowired
    private OrderService orderService;


    @RequestMapping(value = "/cart/cart_item", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    private Payloads.UserCartResponse addCartItem(@RequestHeader("userId") String userId, @RequestBody Payloads.CartItemRequest cartItemRequest) {
        UserCart userCart = userCartService.addItemToCart(userId, cartItemRequest.productId(), cartItemRequest.quantity());
        return new Payloads.UserCartResponse(userCart.userId().value(), new ArrayList<>(userCart.items().values()), userCart.rewardIds());
    }

    @RequestMapping(value = "/cart/discount", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    private UserCart addCoupon(@RequestHeader("userId") String userId, @RequestBody Payloads.CouponItemRequest couponItemRequest) {
        Rule.Discount discount = rewardService.getDiscountByCode(new UserId(userId), couponItemRequest.couponCode());
        userCartService.fetchCart(userId).addReward(discount.discountCode());
        return userCartService.fetchCart(userId);
    }

    @RequestMapping(value = "/cart", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    private Payloads.UserCartResponse getCart(@RequestHeader("userId") String userId) {
        UserCart userCart =   userCartService.fetchCart(userId);
        return new Payloads.UserCartResponse(userCart.userId().value(), new ArrayList<>(userCart.items().values()), userCart.rewardIds());
    }

    @RequestMapping(value = "/cart/checkout", method = RequestMethod.POST)
    @ResponseBody
    private Payloads.OrderResponse checkout(@RequestHeader("userId") String userId) {
        Order order = checkoutService.checkout(userCartService.fetchCart(userId));
        return new Payloads.OrderResponse(order.userId().value(), order.orderItems(), order.itemCount(),
                order.totalAmount(),order.discountAmount(), order.payableAmount(),order.appliedDiscountCoupon());
    }

    @RequestMapping(value = "/rewards", method = RequestMethod.GET)
    @ResponseBody
    private Payloads.DiscountsResponse getRewards(@RequestHeader("userId") String userId) {
        return new Payloads.DiscountsResponse(userId, rewardService.getEligibleDiscounts(new UserId(userId)));
    }




}
