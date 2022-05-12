package com.karol.offerservice.offerMenager.business.exception.bikeType;

public class BikeTypeNotFoundException extends RuntimeException {
    public BikeTypeNotFoundException(String type) {
        super("Bike type "+ type + " is not found");
    }
}
