package com.example.authservice.security.data.entity;

import com.example.authservice.userMenager.api.request.User;
import lombok.*;


import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpiredJwt implements Serializable
{

    private Long jwtId;

    private User user;

    private String jwt;
}

