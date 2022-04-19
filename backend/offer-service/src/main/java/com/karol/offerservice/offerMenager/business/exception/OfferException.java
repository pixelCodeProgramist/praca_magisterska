package com.karol.offerservice.offerMenager.business.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OfferException extends RuntimeException{
    private OfferError offerError;
}
