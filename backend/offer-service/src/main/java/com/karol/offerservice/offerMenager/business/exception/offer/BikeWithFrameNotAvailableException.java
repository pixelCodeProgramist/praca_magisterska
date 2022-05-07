package com.karol.offerservice.offerMenager.business.exception.offer;

public class BikeWithFrameNotAvailableException extends RuntimeException {
    public BikeWithFrameNotAvailableException(String frame) {
        super("Bike with frame "+frame+" is not available now");
    }
}

