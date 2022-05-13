package com.karol.offerservice.offerMenager.api.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ClassicBikePriceRequest {
    @NotNull
    private Integer id;

    @NotBlank
    private String bikeType;

    @NotNull
    private BigDecimal everyBeginHourPrice;

    @NotNull
    private BigDecimal dayPrice;

    @NotNull
    private BigDecimal dayAndNightPrice;

    @NotBlank
    private String currency;
}
