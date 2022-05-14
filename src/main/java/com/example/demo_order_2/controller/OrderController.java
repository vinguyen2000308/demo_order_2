package com.example.demo_order_2.controller;

import com.example.demo_order_2.domain.order.Order;
import com.example.demo_order_2.domain.order.OrderItem;
import com.example.demo_order_2.repo.OrderItemRepo;
import com.example.demo_order_2.repo.OrderRepo;
import com.example.demo_order_2.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemRepo orderItemRepo;

    @Autowired
    private OrderRepo orderRepo;

    @PostMapping("/test-save")
    public Order saveOrder()
    {
        return orderRepo.save(Order.builder()
                        .status(-3)
                        .modifiedDate(LocalDateTime.now())
                        .createdDateTime(LocalDateTime.now())
                .build());
    }

    @GetMapping("/find-by-id")
    public Order findByOrderId(@RequestParam("orderId") Long orderId) {
        return orderService.findByOrderId(orderId);
    }
    @GetMapping("/find-order-item-by-id")
    public OrderItem findOrderItemByOrderId(@RequestParam("orderItemId") Long orderItemId) {
        return orderItemRepo.findById(orderItemId).get();
    }
}
