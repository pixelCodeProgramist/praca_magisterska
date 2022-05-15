package com.example.orderservice.orderMenager.api.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderNameProductForStatisticResponse {
    private String productType;
    private String bikeType;
}
