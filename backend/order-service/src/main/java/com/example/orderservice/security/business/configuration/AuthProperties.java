package com.example.orderservice.security.business.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class AuthProperties
{
    @Value("${app.auth.tokenSecret}")
    private String secretToken;

    @Value("${app.auth.tokenExpirationMsec}")
    private String tokenExpirationMsec;
}