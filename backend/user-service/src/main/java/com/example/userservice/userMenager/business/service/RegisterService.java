package com.example.userservice.userMenager.business.service;

import com.example.userservice.userMenager.api.dto.RoleEnum;
import com.example.userservice.userMenager.api.mapper.RegisterMapper;
import com.example.userservice.userMenager.api.mapper.UserMapper;
import com.example.userservice.userMenager.api.request.RegisterDataRequest;
import com.example.userservice.userMenager.api.request.RegisterRequest;
import com.example.userservice.userMenager.business.exception.role.RoleNotFoundException;
import com.example.userservice.userMenager.business.exception.user.UserMailExistsException;
import com.example.userservice.userMenager.data.entity.Role;
import com.example.userservice.userMenager.data.entity.Token;
import com.example.userservice.userMenager.data.repository.RoleRepo;
import com.example.userservice.userMenager.data.repository.TokenRepo;
import com.example.userservice.userMenager.data.repository.UserRepo;
import com.example.userservice.userMenager.feignClient.MailServiceFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class RegisterService {

    private UserRepo userRepo;
    private RoleRepo roleRepo;

    private TokenService tokenService;
    private PasswordEncoder passwordEncoder;
    private MailServiceFeignClient mailServiceFeignClient;

    @Transactional
    public void register(RegisterRequest registerRequest) {
        isEmailValid(registerRequest.getEmail());
        Token token = tokenService.generateToken();
        String password = registerRequest.getPassword();
        registerRequest.setPassword(passwordEncoder.encode(password));
        Role role = roleRepo.findByRoleIgnoreCase(RoleEnum.CLIENT.name()).orElseThrow(()->new RoleNotFoundException(RoleEnum.CLIENT.name()));
        userRepo.save(UserMapper.mapDataToResponse(registerRequest, role, token, false));
        RegisterDataRequest registerDataRequest = RegisterMapper.mapDataToResponse(registerRequest, token.getToken());
        mailServiceFeignClient.sendMail(registerDataRequest);
    }

    private void isEmailValid(String email) {
        userRepo.findByEmail(email).ifPresent(user -> {
            throw new UserMailExistsException(email);
        });
    }
}
