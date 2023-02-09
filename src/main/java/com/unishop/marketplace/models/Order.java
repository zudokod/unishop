package com.unishop.marketplace.models;

import com.unishop.marketplace.rewards.rules.Rule;

import java.util.List;

/***
 * Record for immutability and this object is store order details
 * @param userId
 * @param orderItems
 * @param itemCount
 * @param totalAmount
 * @param discountAmount
 * @param payableAmount
 * @param appliedDiscountCoupon
 */
public record Order(UserId userId, List<OrderItem> orderItems, int itemCount, double totalAmount, double discountAmount,
                    double payableAmount, Rule.Discount appliedDiscountCoupon) {
    public record OrderItem(String productId, String name, int quantity, double price){}

}
