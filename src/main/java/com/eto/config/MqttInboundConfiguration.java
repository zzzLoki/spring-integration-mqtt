package com.eto.config;

import com.eto.domain.MqttConfigurationProperties;
import com.eto.handler.ReceiverMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import javax.annotation.Resource;


/**
 * 处理从服务器传来的消息
 */
@Configuration
public class MqttInboundConfiguration {

    @Resource
    private MqttConfigurationProperties mqttConfigurationProperties ;

    @Resource
    private ReceiverMessageHandler receiverMessageHandler;


    /**
     * 配置消息传输通道
     * @return
     */

    //1、启动传送带
    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel(); // 创建一条直连传送带
    }

    /**
     * 配置入站适配器
     */
    //2、抓取消息策略(哪一个客户端抓什么样的消息放到传送带上)
    @Bean
    public MessageProducer messageProducer(MqttPahoClientFactory mqttPahoClientFactory) {
        MqttPahoMessageDrivenChannelAdapter adapter  =
                new MqttPahoMessageDrivenChannelAdapter(mqttConfigurationProperties.getUrl() ,
                        mqttConfigurationProperties.getSubClientId() ,
                        mqttPahoClientFactory , mqttConfigurationProperties.getSubTopic().split(",")) ;
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel());// 将抓到的消息放到传送带上
        return adapter ;
    }

    /**
     * 配置入站消息处理器
     * @return
     */

    //3、分拣员（messageHandler）站在传送带尽头，紧盯传送带（绑定到 mqttInputChannel）。
    //
    //每当有包裹到达，分拣员根据《操作手册》（ReceiverMessageHandler 的实现逻辑）处理：
    //
    //如果是“温度传感器包裹”（主题 sensor/temp），记录温度值。
    //
    //如果是“设备报警包裹”（主题 alert/fire），触发消防系统。
    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler messageHandler() {
        return this.receiverMessageHandler ;
    }

}