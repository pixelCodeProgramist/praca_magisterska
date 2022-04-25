package com.example.userservice.userMenager.api.response;

import lombok.*;

import java.math.BigInteger;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserView {
    private Long userId;

    private String firstName;

    private String lastName;

    private String email;

    private BigInteger phone;

    private String password;

    private RoleView role;

    private TokenView token;

    private boolean active;
}
