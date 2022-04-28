package com.example.userservice.userMenager.business.service;

import com.example.userservice.business.service.JwtTokenProvider;
import com.example.userservice.userMenager.api.mapper.UserMapper;
import com.example.userservice.userMenager.api.request.ForgetAndChangerPasswordRequest;
import com.example.userservice.userMenager.api.response.UserView;
import com.example.userservice.userMenager.business.exception.token.TokenAlreadyUsedException;
import com.example.userservice.userMenager.business.exception.token.TokenExpiredException;
import com.example.userservice.userMenager.business.exception.user.UserNotFoundException;
import com.example.userservice.userMenager.data.entity.ExpiredJwt;
import com.example.userservice.userMenager.data.entity.User;
import com.example.userservice.userMenager.data.repository.ExpiredJwtRepo;
import com.example.userservice.userMenager.data.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepo userRepo;

    private ExpiredJwtRepo expiredJwtRepo;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider tokenProvider;

    public void verifyUser(User user) {
        user.setActive(true);
        userRepo.save(user);
    }

    public UserView getUserByMail(String mail) {
        Optional<User> user = userRepo.findByEmail(mail);
        if (user.isPresent()) return UserMapper.mapDataToResponse(user.get());
        return null;
    }

    public UserView getUserById(Long id) {
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent()) return UserMapper.mapDataToResponse(user.get());
        return null;
    }

    public void changePassword(ForgetAndChangerPasswordRequest forgetAndChangerPasswordRequest) {

        Optional<ExpiredJwt> expiredJwtOptional = expiredJwtRepo.findByJwt(forgetAndChangerPasswordRequest.getToken());
        if(expiredJwtOptional.isPresent()) throw new TokenAlreadyUsedException(forgetAndChangerPasswordRequest.getToken());

        if(!tokenProvider.isTokenExpire(forgetAndChangerPasswordRequest.getToken())) {
            Long userId = tokenProvider.extractUserId(forgetAndChangerPasswordRequest.getToken());
            User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException(" with id: " + userId));
            user.setPassword(passwordEncoder.encode(forgetAndChangerPasswordRequest.getPassword()));
            ExpiredJwt expiredJwt = ExpiredJwt.builder()
                    .jwt(forgetAndChangerPasswordRequest.getToken())
                    .user(user)
                    .build();
            expiredJwtRepo.save(expiredJwt);
            userRepo.save(user);
        }else {
            throw new TokenExpiredException(forgetAndChangerPasswordRequest.getToken());
        }

    }
}
