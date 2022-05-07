package com.karol.offerservice.offerMenager.api.response;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AccessoryInformationInOrderView {
    private Long id;

    private String productName;

    private BigDecimal price;

    private String currency;
}
