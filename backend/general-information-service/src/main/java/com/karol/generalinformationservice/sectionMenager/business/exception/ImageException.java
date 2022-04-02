package com.karol.generalinformationservice.sectionMenager.business.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ImageException extends RuntimeException{
    private ImageError imageError;
}
