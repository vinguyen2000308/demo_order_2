package com.example.demo_order_2.controller;

import com.example.demo_order_2.domain.order.Order;
import com.example.demo_order_2.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/find-by-id")
    public Order findByOrderId(@RequestParam("orderId") Long orderId) {
        return orderService.findByOrderId(orderId);
    }
}
