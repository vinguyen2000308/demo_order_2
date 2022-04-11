package com.example.demo_order_2.eventuate_repo;


import com.example.demo_order_2.domain.eventuate.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo extends JpaRepository<Message, String> {
}
