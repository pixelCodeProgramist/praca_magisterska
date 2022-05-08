package com.example.orderservice.orderMenager.api.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderNameProductRequest {
    private Long bikeId;
    private Long accessoryId;
}
