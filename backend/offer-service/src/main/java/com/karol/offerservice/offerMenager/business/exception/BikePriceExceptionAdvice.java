package com.karol.offerservice.offerMenager.business.exception;

import com.karol.offerservice.offerMenager.business.exception.bikePrice.BikePriceArraySizeException;
import com.karol.offerservice.offerMenager.business.exception.bikeType.BikeTypeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class BikePriceExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(BikePriceArraySizeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> bikePriceNotEqualsArrayHandler(BikePriceArraySizeException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("bikePrice", ex.getMessage());
        return errors;
    }


}
