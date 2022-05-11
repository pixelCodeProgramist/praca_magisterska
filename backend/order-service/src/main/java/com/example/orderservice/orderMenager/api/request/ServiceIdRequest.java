package com.example.orderservice.orderMenager.api.request;

import com.example.orderservice.orderMenager.data.entity.UserOrder;
import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ServiceIdRequest {
    private UserOrder userOrder;
    @NotBlank
    private Long id;
}
