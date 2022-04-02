package com.karol.generalinformationservice;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaClient
@EnableEncryptableProperties
public class GeneralInformationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeneralInformationServiceApplication.class, args);
    }

}
