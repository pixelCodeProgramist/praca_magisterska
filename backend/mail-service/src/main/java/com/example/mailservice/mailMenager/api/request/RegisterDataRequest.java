package com.example.mailservice.mailMenager.api.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDataRequest {
    @NotBlank
    @Size(min = 5, max = 100)
    private String subject;

    @NotBlank
    @Size(min = 5, max = 100)
    private String name;

    @NotBlank
    @Email
    private String emailTo;

    @NotBlank
    private String token;
}
