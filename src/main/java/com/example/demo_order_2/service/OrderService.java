package com.example.demo_order_2.service;

import com.example.demo_order_2.common.Const;
import com.example.demo_order_2.domain.command.CreateOrderCommand;
import com.example.demo_order_2.domain.order.Order;
import com.example.demo_order_2.domain.reply.CreateOrderReply;
import com.example.demo_order_2.repo.OrderItemRepo;
import com.example.demo_order_2.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private OrderItemRepo orderItemRepo;

    public Order findByOrderId(Long orderId) {
        return orderRepo.getById(orderId);
    }

    public void cancelOrder(Long orderId) {
        Optional<Order> orderOptional = orderRepo.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setStatus(Const.CANCEL_ORDER_STATUS);
            orderRepo.save(order);
        }
    }


    @Transactional
    public Order findAndUpdateOrderByOrderId(Long orderId) {
        Order orderById = orderRepo.getById(orderId);
        if (Objects.nonNull(orderById)) {
            orderById.setStatus(2);
            orderRepo.save(orderById);
            return orderById;
        } else {
            throw new IllegalArgumentException("Order not found" + orderById);
        }

    }

    @Transactional
    public CreateOrderReply createNewOrder(CreateOrderCommand createOrderCommand) {
        // using createOrderCommand
        Order order = Order.builder()
                .status(Const.ORDER_UPDATING)
                .modifiedDate(LocalDateTime.now())
                .createdDateTime(LocalDateTime.now())
                .build();
        Order savedOrder = orderRepo.save(order);
        Order finalSavedOrder = savedOrder;
        createOrderCommand.getOrderItemList().forEach(orderItem ->
        {
            orderItem.setOrderId(finalSavedOrder.getId());
            orderItemRepo.save(orderItem);
        });
        CreateOrderReply createOrderReply = CreateOrderReply.builder()
                .orderId(savedOrder.getId())
                .code("200")
                .message("ORDER_CREATED")
                .build();
        return createOrderReply;
    }

}


