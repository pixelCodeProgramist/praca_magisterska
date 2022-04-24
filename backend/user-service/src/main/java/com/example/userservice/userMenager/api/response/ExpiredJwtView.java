package com.example.userservice.userMenager.api.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpiredJwtView {
    private String jwt;
}
