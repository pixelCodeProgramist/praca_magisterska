package com.karol.offerservice.offerMenager.api.response.generalInfoPackage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class BikeGeneralOfferResponseView {
    private List<BikeGeneralOfferView> products;

    private int maxPages;

    private BigDecimal minimalPrice;

}
