package com.eto.config;


import com.eto.domain.MqttConfigurationProperties;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;

import javax.annotation.Resource;


/**
 * 用于创建和管理 MQTT 客户端的连接工厂
 * 作用是为 MQTT 客户端提供统一的连接参数（服务器地址、用户名、密码等）
 */
@Configuration
public class MqttConfiguration {

    @Resource
    private MqttConfigurationProperties mqttConfigurationProperties ;

    @Bean
    public MqttPahoClientFactory mqttClientFactory(){

        // 创建客户端工厂
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();

        // 创建MqttConnectOptions对象
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setUserName(mqttConfigurationProperties.getUsername());
        options.setPassword(mqttConfigurationProperties.getPassword().toCharArray());
        options.setServerURIs(new String[]{mqttConfigurationProperties.getUrl()});
        factory.setConnectionOptions(options);

        // 返回
        return factory;
    }

}
