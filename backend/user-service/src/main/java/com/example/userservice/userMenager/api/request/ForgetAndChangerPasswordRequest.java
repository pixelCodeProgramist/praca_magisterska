package com.example.userservice.userMenager.api.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ForgetAndChangerPasswordRequest {
    @NotBlank
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$",
            message = "Hasło musi zawierać 8 znaków w tym: dużą liter, małą literę oraz cyfre")
    private String password;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_.+/=]*$",
            message = "Wprowadzony token nie jest tokenem JWT")
    private String token;
}
