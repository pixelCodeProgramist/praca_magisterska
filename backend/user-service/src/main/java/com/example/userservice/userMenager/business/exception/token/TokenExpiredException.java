package com.example.userservice.userMenager.business.exception.token;

public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException(String token) {
        super("Token: " + token + " is expired");
    }
}
