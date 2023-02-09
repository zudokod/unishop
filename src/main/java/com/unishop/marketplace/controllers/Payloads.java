package com.unishop.marketplace.controllers;

import com.unishop.marketplace.rewards.rules.Rule;
import com.unishop.marketplace.models.UserId;

import java.util.List;

/**
 * Repo for custom payload json
 */
public final class Payloads {


    public static record CartItemRequest(String productId, int quantity){}
    public static record CouponItemRequest(String couponCode){}
    public static record DiscountsResponse(UserId userId, List<Rule.Discount> eligibleDiscounts){}

}
