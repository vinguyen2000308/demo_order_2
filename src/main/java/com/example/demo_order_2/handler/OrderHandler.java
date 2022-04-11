package com.example.demo_order_2.handler;


import com.example.demo_order_2.common.KafkaProducer;
import com.example.demo_order_2.domain.Data;
import com.example.demo_order_2.domain.command.CancelCreateOrderCommand;
import com.example.demo_order_2.domain.command.ConfirmCreateOrderCommand;
import com.example.demo_order_2.domain.command.CreateOrderCommand;
import com.example.demo_order_2.domain.reply.CreateOrderReply;
import com.example.demo_order_2.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.example.demo_order_2.common.MessageUtil.checkCommandType;

@Component
public class OrderHandler {

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderService orderService;


    @KafkaListener(topics = "order-service", groupId = "group1")
    public void orderServiceHandler(String message) throws JsonProcessingException {
        Data data = objectMapper.readValue(message, Data.class);
        if (Objects.nonNull(data)) {
            handleMessage(data);
        }

    }

    private void handleMessage(Data data) throws JsonProcessingException {
        String type = data.getHeader().get("command_type");
        if (checkCommandType(CreateOrderCommand.class, type)) {
            handleCreateOrderCommand(data);
        }
        if (checkCommandType(CancelCreateOrderCommand.class, type)) {
            handleCancelCreateOrderCommand(data);
        }
        if (checkCommandType(ConfirmCreateOrderCommand.class, type)) {
            handleConfirmCreateOrderCommand(data);
        }
        if (checkCommandType(CancelCreateOrderCommand.class, type))
            handleCancelCreateOrderCommand(data);

    }

    private void handleConfirmCreateOrderCommand(Data data) throws JsonProcessingException {
        ConfirmCreateOrderCommand confirmCreateOrderCommand = objectMapper.readValue(data.getPayload(), ConfirmCreateOrderCommand.class);
        orderService.findAndUpdateOrderByOrderId(confirmCreateOrderCommand.getOrderId());
    }

    private void handleCancelCreateOrderCommand(Data data) throws JsonProcessingException {
        CancelCreateOrderCommand cancelCreateOrderCommand = objectMapper.readValue(data.getPayload(), CancelCreateOrderCommand.class);
        orderService.cancelOrder(cancelCreateOrderCommand.getOrderId());
    }


    private void handleCreateOrderCommand(Data data) throws JsonProcessingException {
        CreateOrderCommand createOrderCommand = objectMapper.readValue(data.getPayload(), CreateOrderCommand.class);
        CreateOrderReply createOrderReply = orderService.createNewOrder(createOrderCommand);
        kafkaProducer.sendMessage(CreateOrderReply.class, data, createOrderReply);


    }


}
