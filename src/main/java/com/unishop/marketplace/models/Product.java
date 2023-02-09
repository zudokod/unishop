package com.unishop.marketplace.models;

public record Product(String id, String name, double unitPrice, AvailableStock availableStock) {

    public Product updateStock(AvailableStock newStock){
        return new Product(this.id, this.name, this.unitPrice, newStock);
    }
}
