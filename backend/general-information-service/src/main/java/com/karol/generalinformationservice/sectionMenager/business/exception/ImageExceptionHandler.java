package com.karol.generalinformationservice.sectionMenager.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ImageExceptionHandler {
    @ExceptionHandler(value = ImageException.class)
    public ResponseEntity<?> handleException(ImageException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getImageError().getMessage());
    }
}
