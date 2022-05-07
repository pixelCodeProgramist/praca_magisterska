package com.karol.offerservice.business.service;

import com.karol.offerservice.business.configuration.AuthProperties;
import com.karol.offerservice.business.request.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
    public String generateToken(User user, List<String> keys, List<String> values) {
        Map<String, Object> claims = new HashMap<>();
        for (int i = 0; i < keys.size(); i++)
            claims.put(keys.get(i), values.get(i));
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

