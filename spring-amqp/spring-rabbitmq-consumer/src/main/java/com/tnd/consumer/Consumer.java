package com.tnd.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.tnd.model.Category;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Consumer {

    @RabbitListener(queues = "${tnd.rabbitmq.queue}", containerFactory = "rabbitListenerContainerFactory")
    public void receivedMessage(Category category) {
        log.info("Received msg = " + category);
    }

}
