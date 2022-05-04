package com.example.userservice.userMenager.business.exception.address;

public class AddressNotFoundException extends RuntimeException{
    public AddressNotFoundException(String id) {
        super("Address "+id+" does not exist");
    }
}
