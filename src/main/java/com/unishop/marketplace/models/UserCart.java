package com.unishop.marketplace.models;

import java.util.*;

/**
 * Handles User Cart
 * @param userId
 * @param items
 * @param rewardIds
 */
public record UserCart(UserId userId, Map<String, CartItem> items, List<String> rewardIds) {
    public static UserCart createEmptyCart(String userId) {
        return new UserCart(new UserId(userId), new HashMap<>(), new ArrayList<String>());
    }

    public UserCart updateCart(CartItem item) {

        if (this.items.containsKey(item.product().id())){
            this.items.replace(item.product().id(),item.updatedQuantity(item.quantity()));
        } else {
            this.items.put(item.product().id(), item);
        }

        return this;
    }

    public UserCart addReward(String rewardId) {
        // support only one coupon now
        if(this.rewardIds.size() == 0) this.rewardIds.add(rewardId);
       return this;
    }
}
