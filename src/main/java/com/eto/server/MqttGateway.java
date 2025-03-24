package com.eto.server;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;

/**
 * @Description
 * @Author
 * @Create 8:53
 */

/**
 * 技术价值：
 * 解耦业务代码与消息底层（无需直接操作 MessageChannel 或 MessageHandler）
 */
@MessagingGateway(defaultRequestChannel = "mqttOutputChannel")
public interface MqttGateway {

    /**
     * 发送mqtt消息
     * @param topic 主题
     * @param payload 内容
     */
    void sendToMqtt(@Header(MqttHeaders.TOPIC) String topic, String payload);

    /**
     * 发送包含qos的消息
     * @param topic 主题
     * @param qos 消息处理机制
     * @param payload 消息体
     */
    void sendToMqtt(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos, String payload);

}
