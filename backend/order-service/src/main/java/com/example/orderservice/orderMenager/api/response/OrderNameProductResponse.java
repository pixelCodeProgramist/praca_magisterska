package com.example.orderservice.orderMenager.api.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderNameProductResponse {
    private String bike;
    private String frame;
    private String accessory;
}
