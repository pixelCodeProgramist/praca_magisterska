package com.example.userservice.userMenager.business.exception.user;

public class UserMailExistsException extends RuntimeException {
    public UserMailExistsException(String mail) {
        super("Mail " + mail + " is taken");
    }
}
