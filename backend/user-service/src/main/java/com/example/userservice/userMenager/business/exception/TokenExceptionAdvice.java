package com.example.userservice.userMenager.business.exception;

import com.example.userservice.userMenager.business.exception.token.TokenAlreadyUsedException;
import com.example.userservice.userMenager.business.exception.token.TokenNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class TokenExceptionAdvice {
    @ResponseBody
    @ExceptionHandler(TokenNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> tokenNotFoundHandler(TokenNotFoundException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("token", ex.getMessage());
        return errors;
    }

    @ResponseBody
    @ExceptionHandler(TokenAlreadyUsedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> cannotCreateFilmHandler(TokenAlreadyUsedException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("token", ex.getMessage());
        return errors;
    }
}
