package com.example.orderservice.security.business.service;

import com.example.orderservice.security.business.configuration.AuthProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
@AllArgsConstructor
public class JwtTokenNonUserOrderProvider implements TokenProvider {
    private AuthProperties authProperties;

    public String extractValueFromClaims(String token, String key) {
        Claims claims = extractAllClaims(token, authProperties.getSecretToken());
        return String.valueOf(claims.get(key));
    }
    @Override
    public String extractUserIdName(String token) {
        Claims claims = extractAllClaims(token, authProperties.getSecretToken());
        return claims.getSubject();
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
    public String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setSubject("ORDER")
                .addClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() +
                        Long.parseLong(authProperties.getTokenExpirationMsecOrder())))
                .signWith(SignatureAlgorithm.HS256, authProperties.getSecretToken())
                .compact();
    }

    @Override
    public boolean validateToken(String token) {
        Claims claims = extractAllClaims(token, authProperties.getSecretToken());
        return claims!=null && !isTokenExpire(token);
    }
}

