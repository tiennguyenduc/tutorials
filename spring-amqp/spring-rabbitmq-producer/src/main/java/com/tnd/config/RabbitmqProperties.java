package com.tnd.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "tnd.rabbitmq")
public class RabbitmqProperties {

    private String queue;

    private String exchange;

    private String routingkey;

}
