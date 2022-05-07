package com.example.orderservice.security.business.service;

import com.example.orderservice.userMenager.api.request.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TokenProvider {
    default Long extractUserId(String token){
        return -1L;
    }
    default String extractUserIdName(String token) {
        return "";
    }

    default Claims extractAllClaims(String token, String secretToken) {
        Claims body = null;
        try {
            body = Jwts.parser()
                    .setSigningKey(secretToken)
                    .parseClaimsJws(token).getBody();
            return body;
        }catch (Exception e) {
            return body;
        }
    }

    default  boolean isTokenExpire(String token) {
        return extractExpireDate(token).before(new Date());
    }

    Date extractExpireDate(String token);

    default String generateToken(User user) {
        return "";
    }
    default String generateToken() {
        return "";
    }

    default String generateToken(Map<String, Object> claims) {
        return "";
    }

    default boolean validateToken(String token, User user) {
        Long userId = extractUserId(token);
        return userId.equals(user.getUserId()) && !isTokenExpire(token);
    }

    default boolean validateToken(String token) {
        return false;
    }
}
