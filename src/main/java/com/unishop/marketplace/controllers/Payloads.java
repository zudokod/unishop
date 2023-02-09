package com.unishop.marketplace.controllers;

import com.unishop.marketplace.models.CartItem;
import com.unishop.marketplace.models.Order;
import com.unishop.marketplace.rewards.rules.Rule;
import com.unishop.marketplace.models.UserId;

import java.util.List;
import java.util.Map;

/**
 * Repo for custom payload json
 */
public final class Payloads {


    public static record CartItemRequest(String productId, int quantity){}
    public static record CouponItemRequest(String couponCode){}
    public static record DiscountsResponse(String userId, List<Rule.Discount> eligibleDiscounts){}
    public static record UserCartResponse(String userId, List<CartItem> items, List<String> rewardIds){}
    public static record OrderResponse(String userId, List<Order.OrderItem> orderItems, int itemCount, double totalAmount,
                                       double discountAmount, double payableAmount, Rule.Discount appliedDiscountCoupon){}
    public static record OrderCountResponse(String userid, int orderCount){}


}
