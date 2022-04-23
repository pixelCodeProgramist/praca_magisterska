package com.example.userservice.userMenager.business.exception.user;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String user) {
        super("User "+ user + " is not found");
    }
}
