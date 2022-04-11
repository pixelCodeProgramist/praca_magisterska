package com.karol.generalinformationservice.generalInformationMenager.business.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GeneralInfoError {
    INFO_NOT_FOUND_EXCEPTION("General information does not exist");
    private  String message;
}
