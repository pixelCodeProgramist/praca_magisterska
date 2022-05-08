package com.example.orderservice.orderMenager.business.exception.frame;

public class FrameNotFoundException extends RuntimeException {
    public FrameNotFoundException(String frame, Long bikeId) {
        super("Frame: "+frame+" does not exists in bike id: "+bikeId);
    }
}
