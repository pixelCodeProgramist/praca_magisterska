package com.example.orderservice.security.business.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExpiredJwtResponse {
    private Long jwtId;

    private Long userId;

    private String jwt;
}
