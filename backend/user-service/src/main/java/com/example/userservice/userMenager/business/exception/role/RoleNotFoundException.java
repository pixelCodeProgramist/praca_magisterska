package com.example.userservice.userMenager.business.exception.role;

public class RoleNotFoundException extends RuntimeException{
    public RoleNotFoundException(String role) {
        super("Role does not exist");
    }
}
