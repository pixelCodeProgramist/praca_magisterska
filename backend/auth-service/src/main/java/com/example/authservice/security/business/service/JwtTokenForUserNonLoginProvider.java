package com.example.authservice.security.business.service;

import com.example.authservice.security.business.configuration.AuthProperties;
import com.example.authservice.userMenager.api.request.User;
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
public class JwtTokenForUserNonLoginProvider implements TokenProvider {
    private AuthProperties authProperties;

    @Override
    public Long extractUserId(String token) {
        Claims claims = extractAllClaims(token, authProperties.getSecretToken());
        return Long.parseLong(claims.getSubject());
    }

    @Override
    public Date extractExpireDate(String token) {
        Claims claims = extractAllClaims(token, authProperties.getSecretToken());
        Date expirationDate = claims.getExpiration();
        return expirationDate;
    }

    @Override
    public String generateToken(User user, String key, String value) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(key, value);
        return Jwts.builder()
                .setSubject(user.getUserId().toString())
                .addClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() +
                        Long.parseLong(authProperties.getTokenExpirationMsec())))
                .signWith(SignatureAlgorithm.HS256, authProperties.getSecretToken())
                .compact();
    }
}

