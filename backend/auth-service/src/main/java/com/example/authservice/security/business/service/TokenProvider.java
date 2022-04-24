package com.example.authservice.security.business.service;

import com.example.authservice.userMenager.api.request.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.Date;

public interface TokenProvider {
    Long extractUserId(String token);

    default Claims extractAllClaims(String token, String secretToken) {
        return Jwts.parser()
                .setSigningKey(secretToken)
                .parseClaimsJws(token).getBody();
    }

    default  boolean isTokenExpire(String token) {
        return extractExpireDate(token).before(new Date());
    }

    Date extractExpireDate(String token);

    default String generateToken(User user) {
        return "";
    }

    default boolean validateToken(String token, User user) {
        Long userId = extractUserId(token);
        return userId.equals(user.getUserId()) && !isTokenExpire(token);
    }
}
