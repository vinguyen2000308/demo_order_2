package com.example.demo_inventory_2.handler;

import com.example.demo_inventory_2.common.KafkaProducer;
import com.example.demo_inventory_2.domain.Data;
import com.example.demo_inventory_2.domain.command.CancelUpdateStockTotalCommand;
import com.example.demo_inventory_2.domain.command.ConfirmUpdateStockTotalCommand;
import com.example.demo_inventory_2.domain.command.UpdateStockTotalCommand;
import com.example.demo_inventory_2.domain.reply.UpdateStockTotalReply;
import com.example.demo_inventory_2.service.InventoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.example.demo_inventory_2.common.MessageUtil.checkCommandType;

@Component
public class InventoryHandler {

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private InventoryService inventoryService;

    @KafkaListener(topics = "inventory-service", groupId = "group1")
    public void customerServiceHandler(String message) throws JsonProcessingException {
        System.out.println("Received Message: " + message);
        Data data = objectMapper.readValue(message, Data.class);
        if (Objects.nonNull(data)) {
            handleMessage(data);
        }
    }

    private void handleMessage(Data data) throws JsonProcessingException {
        String type = data.getHeader().get("command_type");
        if (checkCommandType(UpdateStockTotalCommand.class, type))
            handleUpdateStockTotalCommand(data);
        else if (checkCommandType(CancelUpdateStockTotalCommand.class, type))
            handleCancelUpdateStockTotalCommand(data);
        else if (checkCommandType(ConfirmUpdateStockTotalCommand.class, type))
            handleConfirmUpdateStockTotalCommand(data);


    }

    private void handleConfirmUpdateStockTotalCommand(Data data) throws JsonProcessingException {
        ConfirmUpdateStockTotalCommand command = objectMapper.readValue(data.getPayload(), ConfirmUpdateStockTotalCommand.class);
        inventoryService.confirmUpdateStockTotal(command);
    }

    private void handleCancelUpdateStockTotalCommand(Data data) throws JsonProcessingException {
        CancelUpdateStockTotalCommand command = objectMapper.readValue(data.getPayload(), CancelUpdateStockTotalCommand.class);
        inventoryService.cancelUpdateStockTotal(command);

    }

    private void handleUpdateStockTotalCommand(Data data) throws JsonProcessingException {
        UpdateStockTotalCommand command = objectMapper.readValue(data.getPayload(), UpdateStockTotalCommand.class);
        UpdateStockTotalReply reply = inventoryService.updateStockTotal(command);
        kafkaProducer.sendMessage(UpdateStockTotalReply.class, data, reply);

    }

}
