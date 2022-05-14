package com.example.demo_inventory_2.service;

import com.example.demo_inventory_2.domain.OrderItem;
import com.example.demo_inventory_2.domain.Product;
import com.example.demo_inventory_2.domain.StockTotal;
import com.example.demo_inventory_2.domain.command.CancelUpdateStockTotalCommand;
import com.example.demo_inventory_2.domain.command.ConfirmUpdateStockTotalCommand;
import com.example.demo_inventory_2.domain.command.UpdateStockTotalCommand;
import com.example.demo_inventory_2.domain.dto.UpdateStockTotalItemDTO;
import com.example.demo_inventory_2.domain.reply.UpdateStockTotalReply;
import com.example.demo_inventory_2.repo.ProductRepo;
import com.example.demo_inventory_2.repo.StockTotalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class InventoryService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private StockTotalRepo stockTotalRepo;

    public UpdateStockTotalReply updateStockTotal(UpdateStockTotalCommand command) {
        UpdateStockTotalReply updateStockTotalReply = UpdateStockTotalReply.builder().build();

        List<OrderItem> orderItemList = command.getOrderItemList();
        List<UpdateStockTotalItemDTO> updateStockTotalItemDTOS = new ArrayList<>();

//        - validate item va - kho
        Product productByTitle;
        StockTotal stockTotalByProductId;

        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem = orderItemList.get(i);
            String productName = orderItem.getName();
            Integer total = orderItem.getTotal();

            productByTitle = productRepo.findProductByProductId(orderItem.getProductId());
            if (Objects.isNull(productByTitle))
                return UpdateStockTotalReply.builder()
                        .code("400")
                        .message("Not found product with product name " + productName)
                        .build();

            stockTotalByProductId = stockTotalRepo.findStockTotalByProductIdAndStatus(productByTitle.getProductId(), 1l);
            if (Objects.isNull(stockTotalByProductId))
                return UpdateStockTotalReply.builder()
                        .code("400")
                        .message("Stock total are being updated. Try again! ")
                        .build();

            if (stockTotalByProductId.getAvailableQuantity() > total) {
                stockTotalByProductId.setStatus(-3l);
                stockTotalByProductId.setAvailableQuantity(stockTotalByProductId.getAvailableQuantity() - total);
                stockTotalRepo.save(stockTotalByProductId);

                updateStockTotalItemDTOS.add(UpdateStockTotalItemDTO.builder()
                        .productId(productByTitle.getProductId())
                        .price(productByTitle.getBasePrice())
                        .stockTotalId(stockTotalByProductId.getStockTotalId())
                        .quantity(total)
                        .name(productByTitle.getTitle())
                        .build());
            } else {
                return UpdateStockTotalReply.builder()
                        .code("400")
                        .message("Not enough product in stock " + productName)
                        .build();
            }


        }
        updateStockTotalReply.setStockTotalItems(updateStockTotalItemDTOS);
        updateStockTotalReply.setCode("200");
        updateStockTotalReply.setMessage("STOCK TOTAL UPDATED");
        return updateStockTotalReply;
    }

    public void cancelUpdateStockTotal(CancelUpdateStockTotalCommand command) {
        List<UpdateStockTotalItemDTO> stockTotalItems = command.getStockTotalItems();
        for (int i = 0; i < stockTotalItems.size(); i++) {
            UpdateStockTotalItemDTO updateStockInfo = stockTotalItems.get(i);
            StockTotal stockTotal = stockTotalRepo.findStockTotalByStockTotalIdAndStatus(updateStockInfo.getStockTotalId(), -3l);
            stockTotal.setAvailableQuantity(stockTotal.getAvailableQuantity() + updateStockInfo.getQuantity());
            stockTotal.setStatus(1l);
            stockTotalRepo.save(stockTotal);
        }
    }

    public void confirmUpdateStockTotal(ConfirmUpdateStockTotalCommand command) {
        List<UpdateStockTotalItemDTO> stockTotalItems = command.getStockTotalItems();
        for (int i = 0; i < stockTotalItems.size(); i++) {
            UpdateStockTotalItemDTO updateStockInfo = stockTotalItems.get(i);
            StockTotal stockTotal = stockTotalRepo.findStockTotalByStockTotalIdAndStatus(updateStockInfo.getStockTotalId(), -3l);
            stockTotal.setStatus(1l);
            stockTotalRepo.save(stockTotal);
        }

    }
}
