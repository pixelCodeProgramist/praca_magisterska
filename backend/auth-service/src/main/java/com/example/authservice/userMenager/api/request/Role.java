package com.example.authservice.userMenager.api.request;

import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    private Long roleId;

    private String role;
}
