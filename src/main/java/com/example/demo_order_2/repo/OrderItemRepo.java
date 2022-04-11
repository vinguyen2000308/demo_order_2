package com.example.demo_order_2.repo;

import com.example.demo_order_2.domain.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {
}
