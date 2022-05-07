package com.example.orderservice.security.business.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserByMailRequest {
    String mail;
    String token;
}
