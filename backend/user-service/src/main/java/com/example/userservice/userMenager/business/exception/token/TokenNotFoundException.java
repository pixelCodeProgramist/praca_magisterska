package com.example.userservice.userMenager.business.exception.token;

public class TokenNotFoundException extends RuntimeException {
    public TokenNotFoundException(String token) {
        super("Token: " + token + " does not exist");
    }
}
