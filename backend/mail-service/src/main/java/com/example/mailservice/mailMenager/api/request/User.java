package com.example.mailservice.mailMenager.api.request;


import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long userId;

    private String firstName;

    private String lastName;

    private String email;

    private BigInteger phone;

    private String password;

    private Role role;

    private Token token;

    private boolean active;


}
