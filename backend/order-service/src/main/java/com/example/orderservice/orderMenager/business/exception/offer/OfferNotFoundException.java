package com.example.orderservice.orderMenager.business.exception.offer;

public class OfferNotFoundException extends RuntimeException {
    public OfferNotFoundException() {
        super("Offer does not exists");
    }
}
