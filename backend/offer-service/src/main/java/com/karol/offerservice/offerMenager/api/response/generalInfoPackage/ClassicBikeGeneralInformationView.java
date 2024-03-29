package com.karol.offerservice.offerMenager.api.response.generalInfoPackage;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ClassicBikeGeneralInformationView {
    private String productName;

    private BigDecimal everyBeginHourPrice;

    private BigDecimal dayPrice;

    private BigDecimal dayAndNightPrice;

    private String currency;

    private int availableNumber;
}
