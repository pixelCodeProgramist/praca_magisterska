package com.example.userservice.userMenager.api.request;

import lombok.*;

import javax.validation.constraints.*;
import java.math.BigInteger;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Imie nie może być puste")
    @Size(min = 5, max = 50)
    private String firstName;

    @NotBlank(message = "Nazwisko nie może być puste")
    @Size(min = 5, max = 50)
    private String lastName;

    @NotBlank(message = "Email nie może być pusty")
    @Email
    @Size(min = 5, max = 50)
    private String email;

    @NotBlank(message = "Numer telefonu nie może być pusty")
    @Digits(integer = 9, fraction = 0)
    private BigInteger phone;

    @NotBlank(message = "Hasło nie może być puste")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$",
            message = "Hasło musi zawierać 8 znaków w tym: dużą liter, małą literę oraz cyfre")
    private String password;
}
