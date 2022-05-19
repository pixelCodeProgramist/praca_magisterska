package com.example.mailservice.mailMenager.business.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String user) {
        super("User "+ user + " is not found");
    }
}
