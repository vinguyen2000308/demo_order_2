package com.example.demo_inventory_2.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderItem {

    private String name;
    private Integer total;
    private Long productId;
    private Double price;
}
