package com.example.orderservice.orderMenager.business.exception;

import com.example.orderservice.orderMenager.business.exception.date.DateIncorrectException;
import com.example.orderservice.orderMenager.business.exception.frame.FrameNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class DateExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(DateIncorrectException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public Map<String, String> dateTimeException(DateIncorrectException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("date", ex.getMessage());
        return errors;
    }
}
