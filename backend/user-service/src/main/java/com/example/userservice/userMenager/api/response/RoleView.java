package com.example.userservice.userMenager.api.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleView {
    private Long roleId;

    private String role;
}
