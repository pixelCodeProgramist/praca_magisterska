package com.karol.offerservice.offerMenager.api.response;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductGeneralOfferView {
    private int id;

    private String name;

    private byte[] image;

    private BigDecimal rating;
}
