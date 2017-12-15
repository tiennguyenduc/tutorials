package com.tnd.config;

import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.cloud.bus.event.AckRemoteApplicationEvent;
import org.springframework.cloud.bus.event.EnvironmentChangeListener;
import org.springframework.cloud.bus.event.EnvironmentChangeRemoteApplicationEvent;
import org.springframework.cloud.bus.event.RefreshRemoteApplicationEvent;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;
import org.springframework.cloud.bus.event.TraceListener;
import org.springframework.cloud.bus.event.UnknownRemoteApplicationEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

/**
 * Customized MappingJackson2MessageConverter
 * to convert SNAKE_CASE in message to CAMEL_CASE in object Java
 */
//@Configuration
public class RabbitListenerConfig implements RabbitListenerConfigurer {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

        mapper.registerSubtypes(
                AckRemoteApplicationEvent.class,
                EnvironmentChangeListener.class,
                EnvironmentChangeRemoteApplicationEvent.class,
                RefreshRemoteApplicationEvent.class,
                RemoteApplicationEvent.class,
                TraceListener.class,
                UnknownRemoteApplicationEvent.class);

        return mapper;
    }

    ;

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {

        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(objectMapper());

        return converter;

    }

    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(consumerJackson2MessageConverter());
        return factory;
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }
}
