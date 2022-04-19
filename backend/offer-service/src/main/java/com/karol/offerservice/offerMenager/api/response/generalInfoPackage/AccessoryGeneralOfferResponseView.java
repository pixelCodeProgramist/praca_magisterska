package com.karol.offerservice.offerMenager.api.response.generalInfoPackage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class AccessoryGeneralOfferResponseView {
    private List<AccessoryGeneralOfferView> products;

    private int maxPages;

}
