package com.example.demo_inventory_2.domain.command;

import com.example.demo_inventory_2.domain.Command;
import com.example.demo_inventory_2.domain.dto.UpdateStockTotalItemDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class ConfirmUpdateStockTotalCommand implements Command {
    private List<UpdateStockTotalItemDTO> stockTotalItems;
}
