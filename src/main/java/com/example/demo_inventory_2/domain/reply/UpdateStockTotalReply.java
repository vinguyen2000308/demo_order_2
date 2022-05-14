package com.example.demo_inventory_2.domain.reply;

import com.example.demo_inventory_2.domain.dto.UpdateStockTotalItemDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UpdateStockTotalReply implements ReplyMessage {
    private String code;
    private String message;
    //    One product -> one stock total id -> one quanity
    private List<UpdateStockTotalItemDTO> stockTotalItems;


}
