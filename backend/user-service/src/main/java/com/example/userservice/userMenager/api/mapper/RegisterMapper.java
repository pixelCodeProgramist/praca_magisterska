package com.example.userservice.userMenager.api.mapper;

import com.example.userservice.userMenager.api.request.RegisterDataRequest;
import com.example.userservice.userMenager.api.request.RegisterRequest;
import com.example.userservice.userMenager.data.entity.Role;
import com.example.userservice.userMenager.data.entity.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RegisterMapper {
    public RegisterDataRequest mapDataToResponse(RegisterRequest registerRequest, String token){
        return new RegisterDataRequest().builder()
                .name(registerRequest.getFirstName() + " " + registerRequest.getLastName())
                .subject("Weryfikacja rejestracji")
                .emailTo(registerRequest.getEmail())
                .token(token)
                .build();
    }
}
