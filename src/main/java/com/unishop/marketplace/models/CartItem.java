package com.unishop.marketplace.models;

public record CartItem(Product product, long quantity) {
    public CartItem updatedQuantity(long quantity){
        return new CartItem(this.product, quantity);
    }
}
