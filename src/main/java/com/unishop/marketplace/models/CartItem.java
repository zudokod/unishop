package com.unishop.marketplace.models;

public record CartItem(Product product, int quantity) {
    public CartItem updatedQuantity(int quantity){
        return new CartItem(this.product, quantity);
    }
}
