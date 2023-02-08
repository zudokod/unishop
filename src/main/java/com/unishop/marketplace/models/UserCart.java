package com.unishop.marketplace.models;

import java.util.*;

public record UserCart(String userId, Map<String, CartItem> items, List<String> rewardIds) {
    public static UserCart createEmptyCart(String userId) {
        return new UserCart(userId, new HashMap<>(), new ArrayList<>());
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
        rewardIds.add(rewardId);
        return this;
    }
}
