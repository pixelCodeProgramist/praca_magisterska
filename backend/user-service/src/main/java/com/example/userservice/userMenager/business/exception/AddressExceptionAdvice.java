package com.example.userservice.userMenager.business.exception;

import com.example.userservice.userMenager.business.exception.address.AddressNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class AddressExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(AddressNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> cannotCreateFilmHandler(AddressNotFoundException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("address", ex.getMessage());
        return errors;
    }
}
