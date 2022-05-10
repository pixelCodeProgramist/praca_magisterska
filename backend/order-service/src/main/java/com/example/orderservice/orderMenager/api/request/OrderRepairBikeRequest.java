package com.example.orderservice.orderMenager.api.request;

import lombok.*;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderRepairBikeRequest {

    @NotBlank
    private String defect;

    @Future
    private Date beginDate;

    @NotBlank
    private String description;
}
