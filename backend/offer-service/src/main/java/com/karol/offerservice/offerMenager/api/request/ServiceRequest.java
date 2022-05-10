package com.karol.offerservice.offerMenager.api.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ServiceRequest {
    @NotBlank
    private String name;
}
