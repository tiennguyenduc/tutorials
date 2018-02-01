package com.tnd;

import java.security.Principal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

@SpringBootApplication
//@EnableDiscoveryClient
@EnableResourceServer
@RestController
//@SessionAttributes("authorizationRequest")
public class AuthServerApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }

    @RequestMapping("/user/me")
    public Principal user(Principal user) {
        return user;
    }

    @RequestMapping("/greeting")
    public String greeting() {
        return "Hello from auth-server!";
    }

}
