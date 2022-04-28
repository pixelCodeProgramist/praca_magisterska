package com.example.authservice.security.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenForUserNonLoginResponse
{
    private String token;
    private Long userId;
}
