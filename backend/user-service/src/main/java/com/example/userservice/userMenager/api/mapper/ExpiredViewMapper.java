package com.example.userservice.userMenager.api.mapper;

import com.example.userservice.userMenager.api.response.ExpiredJwtView;
import com.example.userservice.userMenager.api.response.UserView;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ExpiredViewMapper {

    public ExpiredJwtView mapDataToResponse(com.example.userservice.security.data.entity.ExpiredJwt expiredJwt){
        return new ExpiredJwtView().builder()
                .jwt(expiredJwt.getJwt())
                .build();
    }
}
