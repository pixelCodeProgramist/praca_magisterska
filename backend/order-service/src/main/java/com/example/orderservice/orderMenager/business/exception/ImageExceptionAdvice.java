package com.example.orderservice.orderMenager.business.exception;

import com.example.orderservice.orderMenager.business.exception.image.ImageNotFoundException;
import com.example.orderservice.orderMenager.business.exception.image.ImageSectionException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ImageExceptionAdvice {
    @ResponseBody
    @ExceptionHandler(ImageSectionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> imageNullSectionHandler(ImageSectionException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("image", ex.getMessage());
        return errors;
    }

    @ResponseBody
    @ExceptionHandler(ImageNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> imageNotFoundHandler(ImageNotFoundException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("image", ex.getMessage());
        return errors;
    }
}
