package com.karol.offerservice.offerMenager.business.exception;


import com.karol.offerservice.offerMenager.business.exception.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class UserExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> cannotCreateFilmHandler(UserNotFoundException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("user", ex.getMessage());
        return errors;
    }
}
