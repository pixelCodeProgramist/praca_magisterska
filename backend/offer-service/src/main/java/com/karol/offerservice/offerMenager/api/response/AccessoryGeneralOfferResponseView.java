package com.karol.offerservice.offerMenager.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class AccessoryGeneralOfferResponseView {
    private List<AccessoryGeneralOfferView> products;

    private int maxPages;

}
