package com.example.userservice.userMenager.business.exception.token;

public class TokenAlreadyUsedException extends RuntimeException {
    public TokenAlreadyUsedException(String token) {
        super("Token: " + token + " has been used");
    }
}
