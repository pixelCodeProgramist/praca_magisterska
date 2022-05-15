package com.karol.offerservice.offerMenager.api.response;

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
