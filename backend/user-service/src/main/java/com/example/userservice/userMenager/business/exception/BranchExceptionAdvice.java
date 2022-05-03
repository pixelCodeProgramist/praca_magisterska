package com.example.userservice.userMenager.business.exception;

import com.example.userservice.userMenager.business.exception.branch.BranchNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class BranchExceptionAdvice {
    @ResponseBody
    @ExceptionHandler(BranchNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> roleNotFoundHandler(BranchNotFoundException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("branch", ex.getMessage());
        return errors;
    }
}
