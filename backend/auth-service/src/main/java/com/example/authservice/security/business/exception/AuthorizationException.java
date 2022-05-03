package com.example.authservice.security.business.exception;

public class AuthorizationException extends RuntimeException{
    public AuthorizationException() {
        super("You have no right to see this user");
    }
}
