package com.example.demo_order_2.domain.order;


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
@Table(name = "orders", schema = "vinv_order")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "created_date")
    private LocalDateTime createdDateTime;
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;
    @Column(name = "status")
    private Integer status;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", createdDateTime=" + createdDateTime +
                ", modifiedDate=" + modifiedDate +
                ", status=" + status +
                '}';
    }
}
