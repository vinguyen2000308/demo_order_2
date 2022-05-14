package com.example.demo_inventory_2.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "stock_total")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class StockTotal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_total_id")
    private Long stockTotalId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "available_quantity")
    private Long availableQuantity;

    @Column(name = "status")
    private Long status;

    @Column(name = "created_user")
    private String createdUser;

    @Column(name = "updated_user")
    private String updatedUser;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;


}
