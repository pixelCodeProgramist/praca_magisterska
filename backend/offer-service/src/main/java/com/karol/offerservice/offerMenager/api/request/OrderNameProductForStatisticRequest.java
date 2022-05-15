package com.karol.offerservice.offerMenager.api.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderNameProductForStatisticRequest {
    @NotNull
    private Long bikeId;
}
