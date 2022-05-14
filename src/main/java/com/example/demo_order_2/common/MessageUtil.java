package com.example.demo_order_2.common;

import com.example.demo_order_2.domain.Command;
import com.example.demo_order_2.domain.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MessageUtil {

    public static Map<String, String> getMessageHeader(Data data, Class replyMessage) {
        String replyMessageId = UUID.randomUUID().toString();
        Map<String, String> messageHeader = new HashMap<>();
        messageHeader.put("message_id", replyMessageId);
        messageHeader.put("reply_to_message_id", data.getHeader().get("message_id"));
        messageHeader.put("type", replyMessage.getSimpleName());
        messageHeader.put("saga_id", data.getHeader().get("saga_id"));
        messageHeader.put("reply_topic", data.getHeader().get("reply_topic"));
        return messageHeader;
    }

    public static boolean checkCommandType(Class<? extends Command> checkedClass, String commandType) {
        return checkedClass.getSimpleName().equals(commandType);
    }
}
