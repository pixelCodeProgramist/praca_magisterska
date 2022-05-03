package com.example.userservice.userMenager.business.exception.branch;

public class BranchNotFoundException extends RuntimeException{
    public BranchNotFoundException(Long id) {
        super("Branch with id"+id+" does not exist");
    }
}
