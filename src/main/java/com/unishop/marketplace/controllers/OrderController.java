package com.unishop.marketplace.controllers;

import com.unishop.marketplace.models.Order;
import com.unishop.marketplace.models.UserId;
import com.unishop.marketplace.service.OrderService;
import com.unishop.marketplace.service.repository.OrderHistoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/orders/count", method = RequestMethod.GET)
    @ResponseBody
    private Payloads.OrderCountResponse getOrderCount(@RequestHeader("userId") String userId) {
        return new Payloads.OrderCountResponse(userId, orderService.getOrderCount(new UserId(userId)));
    }

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    @ResponseBody
    private List<Order> getOrderHistory(@RequestHeader("userId") String userId) {
        return OrderHistoryStore.getOrderHistory(new UserId(userId));
    }
}
