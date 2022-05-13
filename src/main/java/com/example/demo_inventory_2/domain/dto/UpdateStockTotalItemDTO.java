package com.example.demo_inventory_2.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateStockTotalItemDTO {
    private Long stockTotalId;
    private Long productId;
    private Integer quantity;
    private Double price;
    private String name;
}
