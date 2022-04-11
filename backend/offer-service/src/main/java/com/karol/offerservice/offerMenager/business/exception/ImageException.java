package com.karol.offerservice.offerMenager.business.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ImageException extends RuntimeException{
    private ImageError imageError;
}
