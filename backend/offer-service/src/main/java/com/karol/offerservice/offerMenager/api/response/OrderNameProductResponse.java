package com.karol.offerservice.offerMenager.api.response;

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
