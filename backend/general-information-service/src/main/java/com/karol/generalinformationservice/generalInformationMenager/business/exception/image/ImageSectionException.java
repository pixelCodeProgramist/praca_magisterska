package com.karol.generalinformationservice.generalInformationMenager.business.exception.image;

public class ImageSectionException extends RuntimeException {
    public ImageSectionException(String name) {
        super("Section " + name + " cannot be null, empty or blank");
    }
}
