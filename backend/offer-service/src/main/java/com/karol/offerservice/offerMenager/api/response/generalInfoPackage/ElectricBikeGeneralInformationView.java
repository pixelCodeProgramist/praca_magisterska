package com.karol.offerservice.offerMenager.api.response.generalInfoPackage;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ElectricBikeGeneralInformationView {

    private String time;

    private BigDecimal price;

    private String currency;
}
