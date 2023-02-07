package com.unishop.marketplace.models;

public record AvailableStock(long quantity) {
    public AvailableStock update(long quantity){
        return new AvailableStock(this.quantity + quantity);
    }
}
