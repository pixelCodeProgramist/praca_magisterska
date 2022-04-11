package com.karol.generalinformationservice.generalInformationMenager.business.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ImageError {
    MAIN_SECTION_NULL_EMPTY_BLANK_EXCEPTION("Main section cannot be null, empty or blank"),
    IMAGE_NOT_FOUND_EXCEPTION("Image is not found");
    private  String message;
}
