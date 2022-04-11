package com.example.demo_order_2.service;

import com.example.demo_order_2.domain.eventuate.Message;
import com.example.demo_order_2.eventuate_repo.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    @Autowired
    private MessageRepo messageRepo;

    public Message saveMessage(Message message) {

        return messageRepo.save(message);
    }


}
