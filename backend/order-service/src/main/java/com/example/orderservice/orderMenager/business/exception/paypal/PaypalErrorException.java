package com.example.orderservice.orderMenager.business.exception.paypal;

public class PaypalErrorException extends RuntimeException {
    public PaypalErrorException(String message) {
        super(message);
    }
}
