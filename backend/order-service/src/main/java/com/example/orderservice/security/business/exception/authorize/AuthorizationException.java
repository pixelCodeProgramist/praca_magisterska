package com.example.orderservice.security.business.exception.authorize;

public class AuthorizationException extends RuntimeException{
    public AuthorizationException() {
        super("You have no right to see this user");
    }
}
