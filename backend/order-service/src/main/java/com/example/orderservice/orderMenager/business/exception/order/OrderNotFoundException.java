package com.example.orderservice.orderMenager.business.exception.order;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long id) {
        super("Order id: "+id+" does not exists");
    }
}
