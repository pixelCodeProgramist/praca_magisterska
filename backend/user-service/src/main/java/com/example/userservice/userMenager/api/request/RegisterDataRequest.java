package com.example.userservice.userMenager.api.request;

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
    private String subject;

    private String name;

    private String emailTo;

    private String token;
}
