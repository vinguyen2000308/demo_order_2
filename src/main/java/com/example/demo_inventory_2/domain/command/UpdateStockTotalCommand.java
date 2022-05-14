package com.example.demo_inventory_2.domain.command;

import com.example.demo_inventory_2.domain.Command;
import com.example.demo_inventory_2.domain.OrderItem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class UpdateStockTotalCommand implements Command {

    private List<OrderItem> orderItemList;
    private String actionUser;
    private String updatedDate;


}
