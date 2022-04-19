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
public class ContactRequest {
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
    @Size(min = 3, max = 1000)
    private String message;
}
