package com.example.mailservice;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableEncryptableProperties
@EnableFeignClients
public class MailServiceApplication {
	public final static String MAIN_SITE = "http://localhost:9000/";
	public final static String FRONT_SITE = "http://localhost:4200/";
	public static void main(String[] args) {
		SpringApplication.run(MailServiceApplication.class, args);
	}
}
