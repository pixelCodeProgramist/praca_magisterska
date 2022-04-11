package com.karol.offerservice.offerMenager.api.response;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ElectricProductGeneralInformationView {

    private String time;

    private BigDecimal price;

    private String currency;
}
