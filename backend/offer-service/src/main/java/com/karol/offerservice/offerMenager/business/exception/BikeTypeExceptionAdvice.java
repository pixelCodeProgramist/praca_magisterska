package com.karol.offerservice.offerMenager.business.exception;

import com.karol.offerservice.offerMenager.business.exception.bikeType.BikeTypeNotFoundException;
import com.karol.offerservice.offerMenager.business.exception.offer.BikeWithFrameNotAvailableException;
import com.karol.offerservice.offerMenager.business.exception.offer.OfferNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class BikeTypeExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(BikeTypeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> bikeTypeNotFoundHandler(BikeTypeNotFoundException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("bikeType", ex.getMessage());
        return errors;
    }


}
