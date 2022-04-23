package com.example.userservice.userMenager.api.mapper;

import com.example.userservice.userMenager.api.request.RegisterRequest;
import com.example.userservice.userMenager.data.entity.Role;
import com.example.userservice.userMenager.data.entity.Token;
import com.example.userservice.userMenager.data.entity.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {
    public User mapDataToResponse(RegisterRequest registerRequest, Role role, Token token, boolean isActive){
        return new User().builder()
                .email(registerRequest.getEmail())
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .password(registerRequest.getPassword())
                .phone(registerRequest.getPhone())
                .role(role)
                .active(isActive)
                .token(token)
                .build();
    }
}
