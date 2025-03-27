package com.eto.handler;

import com.eto.server.TbLampService;
import com.eto.server.TbLampStatusService;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 处理特定类型的（消息）
 */
@Component
public class ReceiverMessageHandler implements MessageHandler {

    @Resource
    private TbLampService tbLampService;
    @Resource
    private TbLampStatusService tbLampStatusService;

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        MessageHeaders headers = message.getHeaders();
        String receivedTopicName = (String) headers.get("mqtt_receivedTopic");
        if("autel/iot/lamp/line".equals(receivedTopicName)) {
            tbLampService.updateLampOnlineStatus(message.getPayload().toString()) ;        // 更新智能灯泡的上线状态
        }else if("autel/iot/lamp/device/status".equals(receivedTopicName)) {
            tbLampStatusService.saveDeviceStatus(message.getPayload().toString()) ;
        }
    }
}
