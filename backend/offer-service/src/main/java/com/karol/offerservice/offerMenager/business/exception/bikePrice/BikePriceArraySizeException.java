package com.karol.offerservice.offerMenager.business.exception.bikePrice;

public class BikePriceArraySizeException extends RuntimeException {
    public BikePriceArraySizeException() {
        super("Arrays have got different size");
    }
}
