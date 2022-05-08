package com.example.orderservice.orderMenager.business.exception;

import com.example.orderservice.orderMenager.business.exception.order.CannotCreateQRException;
import com.example.orderservice.orderMenager.business.exception.order.OrderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class OrderExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> dateTimeException(OrderNotFoundException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("order", ex.getMessage());
        return errors;
    }

    @ResponseBody
    @ExceptionHandler(CannotCreateQRException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> cannotCreateQrException(CannotCreateQRException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("qrCode", ex.getMessage());
        return errors;
    }
}
