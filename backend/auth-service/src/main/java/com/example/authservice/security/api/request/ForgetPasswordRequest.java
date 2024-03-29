package com.example.authservice.security.api.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ForgetPasswordRequest {
    @NotBlank
    @Size(min = 5, max = 100)
    private String mail;

    @NotBlank
    private String token;
}
