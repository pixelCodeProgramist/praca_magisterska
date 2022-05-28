package com.example.userservice.userMenager.api.request;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigInteger;
import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetailUserRequest {
    @NotBlank
    @Size(min = 5, max = 50)
    private String firstName;

    @NotBlank
    @Size(min = 5, max = 50)
    private String lastName;

    @NotBlank
    @Email
    @Size(min = 5, max = 50)
    private String email;

    @NotBlank
    @Email
    @Size(min = 5, max = 50)
    private String prevEmail;

    private String password;

    @NotBlank
    @Digits(integer = 9, fraction = 0)
    private BigInteger phone;

    @NotBlank(message = "Data nie może być pusta")
    @Past(message = "Data urodzenia nie może być z przyszłości")
    private Date birthDay;

    @Valid
    private AddressRequest addressView;

}
