package com.karol.offerservice.offerMenager.api.dto;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TimePriceDto {
    private String time;
    private BigDecimal price;
}
