package com.example.userservice.userMenager.api.mapper;

import com.example.userservice.userMenager.data.entity.Token;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TokenMapper {
    public static Token mapStringToData(String token) {
        return new Token().builder()
                .token(token)
                .active(true)
                .build();
    }
}
