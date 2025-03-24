package com.eto.server;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @Description 消息发送服务
 * @Author
 * @Create 8:54
 */
@Component
@AllArgsConstructor
public class MqttMessageSender {

    private MqttGateway mqttGateway;

    /**
     * 发送mqtt消息
     * @param topic 主题
     * @param message 内容
     */
    public void send(String topic, String message) {
        mqttGateway.sendToMqtt(topic, message);
    }

    /**
     * 发送包含qos的消息
     * @param topic 主题
     * @param qos 质量
     * @param message 消息体
     */
    public void send(String topic, int qos, byte[] message){
        mqttGateway.sendToMqtt(topic, qos, message.toString());
    }
}
