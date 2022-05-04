package com.example.userservice.userMenager.api.request;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigInteger;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {
    @NotBlank(message = "Imie nie może być puste")
    private Long userId;

    @NotBlank(message = "Imie nie może być puste")
    @Size(min = 5, max = 50, message = "Imie musi mieć przynajmniej pięć znaków i maksymalnie pięćdziesiąt znaków")
    private String firstName;

    @NotBlank(message = "Nazwisko nie może być puste")
    @Size(min = 5, max = 50, message = "Nazwisko musi mieć przynajmniej pięć znaków i maksymalnie pięćdziesiąt znaków")
    private String lastName;

    @NotBlank(message = "Email nie może być pusty")
    @Email
    @Size(min = 5, max = 50, message = "Email musi mieć przynajmniej pięć znaków i maksymalnie pięćdziesiąt znaków")
    private String email;

    @NotBlank(message = "Numer telefonu nie może być pusty")
    @Digits(integer = 9, fraction = 0)
    private BigInteger phone;

    @NotBlank(message = "Hasło nie może być puste")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$",
            message = "Hasło musi zawierać 8 znaków w tym: dużą liter, małą literę oraz cyfre")
    private String password;

    @NotBlank(message = "Data nie może być pusta")
    @Past(message = "Data urodzenia nie może być z przyszłości")
    private Date birthDay;

    @Valid
    private AddressRequest addressView;


    private Boolean isAdmin;

}
