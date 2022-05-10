package com.example.orderservice.orderMenager.api.response;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ServiceGeneralInfoView {
    private Long id;
    private String name;
    private BigDecimal price;
    private String currency;
}
