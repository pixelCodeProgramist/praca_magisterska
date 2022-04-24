package com.example.userservice.userMenager.api.mapper;

import com.example.userservice.userMenager.api.request.RegisterRequest;
import com.example.userservice.userMenager.api.response.UserView;
import com.example.userservice.userMenager.data.entity.Role;
import com.example.userservice.userMenager.data.entity.Token;
import com.example.userservice.userMenager.data.entity.User;
import lombok.experimental.UtilityClass;

import java.util.stream.Collectors;

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

    public UserView mapDataToResponse(User user){
        return new UserView().builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .password(user.getPassword())
                .phone(user.getPhone())
                .role(RoleMapper.mapRoleToData(user.getRole()))
                .expiredJwts(user.getExpiredJwts().stream()
                        .map(jwt->ExpiredViewMapper.mapDataToResponse(jwt))
                        .collect(Collectors.toSet()))
                .active(user.isActive())
                .token(TokenMapper.mapTokenToData(user.getToken()))
                .build();
    }
}
