package com.example.userservice.userMenager.business.exception;

import com.example.userservice.userMenager.business.exception.role.RoleNotFoundException;
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
public class RoleExceptionAdvice {
    @ResponseBody
    @ExceptionHandler(RoleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> roleNotFoundHandler(RoleNotFoundException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("role", ex.getMessage());
        return errors;
    }
}
