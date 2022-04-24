package com.example.userservice.userMenager.api.response;

import com.example.userservice.userMenager.data.entity.Role;
import lombok.*;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

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

    Set<ExpiredJwtView> expiredJwts = new HashSet<>();
}
