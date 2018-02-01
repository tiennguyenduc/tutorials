package com.tnd.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tnd.config.RabbitmqProperties;
import com.tnd.model.Category;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Producer {

    @Autowired
    RabbitmqProperties rabbitmqProperties;
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void produce(Category category) {
        amqpTemplate.convertAndSend(rabbitmqProperties.getExchange(), rabbitmqProperties.getRoutingkey(), category);
//        amqpTemplate.convertAndSend("queue", category);
        log.info("Send msg = " + category);
    }

}
