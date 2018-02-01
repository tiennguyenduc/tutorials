package com.tnd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
//@EnableEurekaClient
//@EnableResourceServer
@RestController
@EnableResourceServer
public class UiClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(UiClientApplication.class, args);
    }

    @RequestMapping("/greeting")
    public String greeting() {
        return "Hello from ui-client!";
    }

}
