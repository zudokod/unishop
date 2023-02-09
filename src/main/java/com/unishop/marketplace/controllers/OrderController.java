package com.unishop.marketplace.controllers;

import com.unishop.marketplace.models.UserId;
import com.unishop.marketplace.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/orders/count", method = RequestMethod.GET)
    @ResponseBody
    private Payloads.OrderCountResponse getOrderCount(@RequestHeader("userId") String userId) {
        return new Payloads.OrderCountResponse(userId, orderService.getOrderCount(new UserId(userId)));
    }
}
