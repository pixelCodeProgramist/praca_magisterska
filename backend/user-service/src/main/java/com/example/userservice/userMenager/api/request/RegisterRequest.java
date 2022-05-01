package com.example.userservice.userMenager.api.request;

import lombok.*;

import javax.validation.constraints.*;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
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
    private Date birthday;

    @NotBlank(message = "Ulica nie może być pusta")
    @Size(min = 3, max = 100, message = "Ulica musi mieć przynajmniej trzy znaki i maksymalnie sto znaków")
    private String street;

    private String houseNr;

    @NotBlank(message = "Kod pocztowy nie może być pusty")
    @Pattern(regexp = "^\\d{2}[- ]{0,1}\\d{3}$",
            message = "Kod pocztowy musi mieć strukturę xx-xxx lub xxxxx i składać się z cyfr")
    private String zipCode;

    @NotBlank(message = "Ulica nie może być pusta")
    @Size(min = 5, max = 100, message = "Ulica musi mieć przynajmniej pięć znaków i maksymalnie sto znaków")
    private String city;

}
