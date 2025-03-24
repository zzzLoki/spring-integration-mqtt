package com.eto.domain;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.mqtt")
@Data
public class MqttConfigurationProperties {


    private String username;
    private String password;
    private String url;
    private String subClientId ;
    private String subTopic ;
    private String pubClientId ;

}
