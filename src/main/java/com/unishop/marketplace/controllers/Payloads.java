package com.unishop.marketplace.controllers;

public final class Payloads {

    public static record CartItemRequest(String productId, long quantity){}
}
