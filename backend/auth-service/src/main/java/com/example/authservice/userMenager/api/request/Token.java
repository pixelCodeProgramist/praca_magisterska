package com.example.authservice.userMenager.api.request;

import lombok.*;




@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    private Long tokenId;

    private String token;

    private Boolean active;

}
