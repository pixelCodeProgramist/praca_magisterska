package com.example.userservice.business.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AuthProperties
{
    @Value("${app.auth.tokenSecret}")
    private String secretToken;

    @Value("${app.auth.tokenExpirationMsec}")
    private String tokenExpirationMsec;
}