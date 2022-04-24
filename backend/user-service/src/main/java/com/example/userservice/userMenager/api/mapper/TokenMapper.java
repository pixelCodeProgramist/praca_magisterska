package com.example.userservice.userMenager.api.mapper;

import com.example.userservice.userMenager.api.response.TokenView;
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

    public static TokenView mapTokenToData(Token token) {
        return new TokenView().builder()
                .token(token.getToken())
                .active(token.getActive())
                .build();
    }
}
