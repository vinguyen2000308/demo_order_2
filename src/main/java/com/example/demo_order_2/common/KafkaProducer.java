package com.example.demo_order_2.common;

import com.example.demo_order_2.domain.Data;
import com.example.demo_order_2.domain.reply.ReplyMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import static com.example.demo_order_2.common.MessageUtil.getMessageHeader;

@Component
public class KafkaProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendMessage(Class<? extends ReplyMessage> replyMessageClass, Data data, ReplyMessage replyMessage) throws JsonProcessingException {
        String reply_topic = data.getHeader().get("reply_topic");
        Data sendData = Data.builder()
                .header(getMessageHeader(data, replyMessageClass))
                .payload(objectMapper.writeValueAsString(replyMessage))
                .build();
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(reply_topic, objectMapper.writeValueAsString(sendData));
        callBack(future, replyMessage);
    }

    private void callBack(ListenableFuture<SendResult<String, String>> future, ReplyMessage replyMessage) {
        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("Sent message=[" + replyMessage + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=[" + replyMessage + "] due to : " + ex.getMessage());
            }
        });
    }
}
