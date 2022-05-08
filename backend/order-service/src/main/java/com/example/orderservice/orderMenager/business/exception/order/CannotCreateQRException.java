package com.example.orderservice.orderMenager.business.exception.order;

public class CannotCreateQRException extends RuntimeException {
    public CannotCreateQRException() {
        super("Cannot create QR image for some reason. Please contact cinema admin");
    }
}