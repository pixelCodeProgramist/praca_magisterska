package com.example.orderservice.security.business.service;

import com.example.orderservice.security.business.configuration.AuthProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class JwtTokenNonUserProvider implements TokenProvider {
    private AuthProperties authProperties;

    @Override
    public Long extractUserId(String token) {
        Claims claims = extractAllClaims(token, authProperties.getSecretToken());
        return Long.parseLong(claims.getSubject());
    }

    @Override
    public Date extractExpireDate(String token) {
        Claims claims = extractAllClaims(token, authProperties.getSecretToken());
        if(claims!=null) {
            Date expirationDate = claims.getExpiration();
            return expirationDate;
        }
        final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;
        return new Date(new Date().getTime() - MILLIS_IN_A_DAY);
    }

    @Override
    public String generateToken() {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setSubject("COMPUTER")
                .addClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() +
                        Long.parseLong(authProperties.getTokenExpirationMsec())))
                .signWith(SignatureAlgorithm.HS256, authProperties.getSecretToken())
                .compact();
    }
}

