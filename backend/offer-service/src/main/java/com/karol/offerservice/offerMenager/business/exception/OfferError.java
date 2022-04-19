package com.karol.offerservice.offerMenager.business.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OfferError {
    OFFER_NOT_FOUND_EXCEPTION("Offer is not found");
    private  String message;
}
