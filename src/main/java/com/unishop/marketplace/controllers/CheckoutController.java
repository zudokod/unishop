package com.unishop.marketplace.controllers;

import com.unishop.marketplace.models.CartItem;
import com.unishop.marketplace.models.Product;
import com.unishop.marketplace.models.UserCart;
import com.unishop.marketplace.service.CheckoutService;
import com.unishop.marketplace.service.ProductCatalogService;
import com.unishop.marketplace.service.UserCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private UserCartService userCartService;

    @Autowired
    private ProductCatalogService catalogService;


    @RequestMapping(value = "/user/{userId}/cart/cart_item", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    private UserCart addCartItem(@PathVariable String userId, @RequestBody Payloads.CartItemRequest cartItemRequest) {
        Product product = catalogService.findProductById(cartItemRequest.productId());
        return userCartService.addItemToCart(userId, new CartItem(product, cartItemRequest.quantity()));
    }

    @RequestMapping(value = "/user/{userId}/cart", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    private UserCart getCart(@PathVariable String userId) {
        return  userCartService.fetchCart(userId);
    }

    @RequestMapping(value = "/user/{userId}/cart/checkout/", method = RequestMethod.GET)
    @ResponseBody
    private UserCart checkout(@PathVariable String userId) {
        checkoutService.checkout(userCartService.fetchCart(userId));
        return null;
    }

    @RequestMapping(value = "/user/{userId}/rewards", method = RequestMethod.GET)
    @ResponseBody
    private UserCart getRewards( @PathVariable String userId) {
        checkoutService.checkout(userCartService.fetchCart(userId));
        return null;
    }

}
