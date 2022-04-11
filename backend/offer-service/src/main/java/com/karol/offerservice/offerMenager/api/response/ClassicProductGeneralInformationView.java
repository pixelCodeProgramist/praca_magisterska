package com.karol.offerservice.offerMenager.api.response;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ClassicProductGeneralInformationView {
    private String productName;

    private BigDecimal everyBeginHourPrice;

    private BigDecimal dayPrice;

    private BigDecimal dayAndNightPrice;

    private String currency;
}
