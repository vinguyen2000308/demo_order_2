package com.example.demo_order_2.domain.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "order_item",schema = "vinv_order")
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "order_item_name")
    private String name;
    @Column(name = "order_item_price")
    private Double price;
}
