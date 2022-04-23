package com.karol.generalinformationservice.generalInformationMenager.business.exception.image;

public class ImageNotFoundException extends RuntimeException {
    public ImageNotFoundException(String name) {
        super("Image "+name+" does not exists");
    }
}
