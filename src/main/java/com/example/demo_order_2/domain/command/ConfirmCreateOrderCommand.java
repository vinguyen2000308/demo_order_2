package com.example.demo_order_2.domain.command;

import com.example.demo_order_2.domain.Command;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class ConfirmCreateOrderCommand implements Command {

    private Long orderId;
}
