package com.example.orderservice.orderMenager.business.exception;

import com.example.orderservice.orderMenager.business.exception.offer.OfferNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class OfferExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(OfferNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> offerNotFoundHandler(OfferNotFoundException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("offer", ex.getMessage());
        return errors;
    }
}
