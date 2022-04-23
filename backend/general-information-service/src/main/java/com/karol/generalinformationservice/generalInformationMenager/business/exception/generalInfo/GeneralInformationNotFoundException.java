package com.karol.generalinformationservice.generalInformationMenager.business.exception.generalInfo;

public class GeneralInformationNotFoundException extends RuntimeException {
    public GeneralInformationNotFoundException() {
        super("General information does not exists");
    }
}
