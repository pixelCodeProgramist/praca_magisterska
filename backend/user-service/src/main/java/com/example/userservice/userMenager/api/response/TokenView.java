package com.example.userservice.userMenager.api.response;

import lombok.*;

import javax.persistence.Column;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenView {

    private String token;

    private Boolean active;
}
