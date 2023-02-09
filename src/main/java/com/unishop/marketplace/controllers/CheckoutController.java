package com.unishop.marketplace.controllers;

import com.unishop.marketplace.models.CartItem;
import com.unishop.marketplace.models.Order;
import com.unishop.marketplace.models.Product;
import com.unishop.marketplace.models.UserCart;
import com.unishop.marketplace.models.UserId;
import com.unishop.marketplace.rewards.rules.UserRewardService;
import com.unishop.marketplace.service.CheckoutService;
import com.unishop.marketplace.service.OrderService;
import com.unishop.marketplace.service.ProductCatalogService;
import com.unishop.marketplace.service.UserCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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


    @RequestMapping(value = "/user/{userId}/cart/cart_item", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    private UserCart addCartItem(@PathVariable String userId, @RequestBody Payloads.CartItemRequest cartItemRequest) {
        Product product = catalogService.findProductById(cartItemRequest.productId());
        return userCartService.addItemToCart(userId, new CartItem(product, cartItemRequest.quantity()));
    }

    @RequestMapping(value = "/user/{userId}/cart/discount", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    private UserCart addCoupon(@PathVariable String userId, @RequestBody Payloads.CouponItemRequest couponItemRequest) {
        userCartService.fetchCart(userId).addReward(couponItemRequest.couponCode());
        return userCartService.fetchCart(userId);
    }

    @RequestMapping(value = "/user/{userId}/cart", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    private UserCart getCart(@PathVariable String userId) {
        return  userCartService.fetchCart(userId);
    }

    @RequestMapping(value = "/user/{userId}/cart/checkout", method = RequestMethod.GET)
    @ResponseBody
    private Order checkout(@PathVariable String userId) {
        return checkoutService.checkout(userCartService.fetchCart(userId));
    }

    @RequestMapping(value = "/user/{userId}/rewards", method = RequestMethod.GET)
    @ResponseBody
    private Payloads.DiscountsResponse getRewards(@PathVariable String userId) {
        return new Payloads.DiscountsResponse(new UserId(userId), rewardService.getEligibleDiscounts(new UserId(userId)));
    }

    @RequestMapping(value = "/user/{userId}/orders/count", method = RequestMethod.GET)
    @ResponseBody
    private int getOrderCount(@PathVariable String userId) {
       return orderService.getOrderCount(new UserId(userId));
    }


}
