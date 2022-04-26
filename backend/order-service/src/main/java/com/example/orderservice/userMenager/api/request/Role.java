package com.example.orderservice.userMenager.api.request;

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
