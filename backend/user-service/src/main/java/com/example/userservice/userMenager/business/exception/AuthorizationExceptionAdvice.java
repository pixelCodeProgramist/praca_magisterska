package com.example.userservice.userMenager.business.exception;

import com.example.userservice.userMenager.business.exception.authorize.AuthorizationException;
import com.example.userservice.userMenager.business.exception.user.UserMailExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class AuthorizationExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> cannotSeeUserHandler(AuthorizationException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("unauthorize", ex.getMessage());
        return errors;
    }
}
