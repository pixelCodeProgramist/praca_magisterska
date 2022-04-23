package com.example.mailservice;

import com.example.mailservice.mailMenager.business.service.EmailSenderService;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.event.EventListener;

import javax.mail.MessagingException;

@SpringBootApplication
@EnableEurekaClient
@EnableEncryptableProperties
public class MailServiceApplication {
	public final static String MAIN_SITE = "http://localhost:9000/";
	public static void main(String[] args) {
		SpringApplication.run(MailServiceApplication.class, args);
	}
}
