package com.eto.handler;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

/**
 * 分拣员处理特定类型的（消息）
 */
@Component
public class ReceiverMessageHandler implements MessageHandler {

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        MessageHeaders headers = message.getHeaders();
        String receivedTopicName = (String) headers.get("mqtt_receivedTopic");
        if ("atguigu/iot/lamp/line".equals(receivedTopicName)) {
            System.out.println("接收到消息：" + message.getPayload());
        }
    }
}
