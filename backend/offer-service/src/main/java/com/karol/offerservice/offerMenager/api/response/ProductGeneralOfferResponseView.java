package com.karol.offerservice.offerMenager.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ProductGeneralOfferResponseView {
    private List<ProductGeneralOfferView> products;

    private int maxPages;

    private BigDecimal minimalPrice;

}
