package com.unishop.marketplace.models;

import java.util.List;

public record Order(String userId, List<OrderItem> orderItems) {

    public record OrderItem(String productId, int quantity){}
}
