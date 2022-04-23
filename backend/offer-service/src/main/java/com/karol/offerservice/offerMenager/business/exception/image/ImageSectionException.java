package com.karol.offerservice.offerMenager.business.exception.image;

public class ImageSectionException extends RuntimeException {
    public ImageSectionException() {
        super("Main section cannot be null, empty or blank");
    }
}
