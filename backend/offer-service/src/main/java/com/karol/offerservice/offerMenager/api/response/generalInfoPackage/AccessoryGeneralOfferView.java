package com.karol.offerservice.offerMenager.api.response.generalInfoPackage;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AccessoryGeneralOfferView {
    private int id;

    private String name;

    private byte[] image;

    private BigDecimal rating;

    private BigDecimal minimalPrice;


}
