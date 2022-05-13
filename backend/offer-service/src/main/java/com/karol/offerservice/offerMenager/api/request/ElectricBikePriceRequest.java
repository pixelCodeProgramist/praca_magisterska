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
public class ElectricBikePriceRequest {
    @NotNull
    private Long id;

    @NotBlank
    private String time;

    @NotNull
    private BigDecimal price;

    @NotNull
    private String currency;
}
