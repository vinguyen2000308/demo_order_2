package com.example.demo_order_2.domain.command;

import com.example.demo_order_2.domain.Command;
import com.example.demo_order_2.domain.order.OrderItem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;


import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class CreateOrderCommand implements Command {

    private Long customerId;
    private Boolean isNewCustomer;
    private List<OrderItem> orderItemList;
}
