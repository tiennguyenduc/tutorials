package com.tnd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.tnd.config.customized.bus.CustomBusJacksonMessageConverter;

@SpringBootApplication
public class ConfigClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigClientApplication.class, args);
    }

//    @Bean(name = "busJsonConverter")
//    public CustomBusJacksonMessageConverter busJsonConverter() {
//        return new CustomBusJacksonMessageConverter();
//    }

}