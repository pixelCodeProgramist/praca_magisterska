package com.example.userservice;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableEurekaClient
@EnableEncryptableProperties
@EnableFeignClients
@EnableScheduling
public class UserServiceApplication {
    public final static String FRONT_SITE = "http://localhost:4200/";

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
