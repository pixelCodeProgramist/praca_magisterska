package com.karol.generalinformationservice.generalInformationMenager.business.exception;

import com.karol.generalinformationservice.generalInformationMenager.business.exception.generalInfo.GeneralInformationNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GeneralInformationExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(GeneralInformationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> generalInformationNotFoundHandler(GeneralInformationNotFoundException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("generalInformation", ex.getMessage());
        return errors;
    }
}
