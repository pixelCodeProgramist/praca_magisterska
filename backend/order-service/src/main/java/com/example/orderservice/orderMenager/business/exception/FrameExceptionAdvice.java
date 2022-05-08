package com.example.orderservice.orderMenager.business.exception;

import com.example.orderservice.orderMenager.business.exception.frame.FrameNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class FrameExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(FrameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> frameNotFoundHandler(FrameNotFoundException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("frame", ex.getMessage());
        return errors;
    }
}
