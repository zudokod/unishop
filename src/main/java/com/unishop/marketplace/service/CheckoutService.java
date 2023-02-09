package com.unishop.marketplace.service;

import com.unishop.marketplace.models.Order;
import com.unishop.marketplace.models.UserCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckoutService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderAdjustmentService adjustmentService;



    public Order checkout(UserCart userCart){

        List<Order.OrderItem> orderItems = userCart.items().values().stream().map(cartItem ->
                new Order.OrderItem(cartItem.product().id(), cartItem.product().name(), cartItem.quantity(),
                        cartItem.product().unitPrice())).toList();

        final double originalAmount =  orderItems.stream().mapToDouble(it -> it.price() * it.quantity()).sum();
        final int itemCount = orderItems.stream().mapToInt(Order.OrderItem::quantity).sum();

        Order order = new Order(userCart.userId(), orderItems, itemCount, originalAmount, 0,  originalAmount, null);
        order = adjustmentService.calculateOrder(order, userCart);
        orderService.processOrder(order);
        return order;

    }


}
