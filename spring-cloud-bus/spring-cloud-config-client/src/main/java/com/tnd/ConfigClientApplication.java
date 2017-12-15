package com.tnd;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tnd.config.CustomBusJacksonMessageConverter;
import com.tnd.schedule.Task;

@SpringBootApplication
public class ConfigClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigClientApplication.class, args);
    }

    @Bean(name = "busJsonConverter")
    public CustomBusJacksonMessageConverter busJsonConverter() {
        return new CustomBusJacksonMessageConverter();
    }

}

@RefreshScope
@RestController
class MessageRestController {

    @Value("${message:Hello default}")
    private String message;

    @RequestMapping("/message")
    Task getMessage() {
        Task result = new Task();
        result.setNameJob("???");

        return result;
    }
}