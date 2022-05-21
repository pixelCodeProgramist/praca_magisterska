package com.example.authservice.security.business.service;

import com.example.authservice.security.api.request.*;
import com.example.authservice.security.api.response.AuthenticationResponse;
import com.example.authservice.security.api.response.TokenForUserNonLoginResponse;
import com.example.authservice.security.business.exception.AuthorizationException;
import com.example.authservice.userMenager.api.request.User;
import com.example.authservice.userMenager.business.exception.user.UserNotFoundException;
import com.example.authservice.userMenager.data.entity.ExpiredJwt;
import com.example.authservice.userMenager.data.repository.ExpiredJwtRepo;
import com.example.authservice.userMenager.feignClient.UserServiceFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SecurityService {

    private UserServiceFeignClient userServiceFeignClient;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider tokenProvider;
    private JwtTokenForUserNonLoginProvider tokenForUserNonLoginProvider;
    private JwtTokenNonUserProvider tokenNonUserProvider;
    
    private ExpiredJwtRepo expiredJwtRepo;

    public AuthenticationResponse logIn(AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getLogin(),
                            authenticationRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Incorrect login or password");
        }

        User user = userServiceFeignClient.getUserByMail(new UserByMailRequest(authenticationRequest.getLogin(), tokenNonUserProvider.generateToken()));

        if (user != null) {
            String token = tokenProvider.generateToken(user);
            return new AuthenticationResponse().builder()
                    .token(token)
                    .name(user.getFirstName() + " " + user.getLastName())
                    .mail(user.getEmail())
                    .userId(user.getUserId())
                    .role(user.getRole().getRole())
                    .build();
        } else
            throw new UserNotFoundException(authenticationRequest.getLogin());
    }

    public void logOut(HttpServletRequest httpServletRequest, boolean isTest) {
        String jwt = null;
        String header = httpServletRequest.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer "))
            jwt = header.substring(7);

        if (SecurityContextHolder.getContext().getAuthentication() != null || isTest) {
            User user = null;

            if(!isTest)
                user = userServiceFeignClient.getUserById(new UserByIdRequest(tokenProvider.extractUserId(jwt),tokenNonUserProvider.generateToken()));
            else {
                user = new User();
                user.setUserId(1L);
            }
            if (user == null) throw new UserNotFoundException(" with id: " + tokenProvider.extractUserId(jwt));
            ExpiredJwt expiredJwt = new ExpiredJwt().builder()
                    .userId(user.getUserId())
                    .jwt(jwt)
                    .build();
            expiredJwtRepo.save(expiredJwt);
        }
    }

    public TokenForUserNonLoginResponse generateTokenNonLoginForUser(ForgetPasswordRequest forgetPasswordRequest, boolean isTest) {
        User user = null;
        if(!isTest)
            user = userServiceFeignClient.getUserByMail(new UserByMailRequest(forgetPasswordRequest.getMail(), tokenNonUserProvider.generateToken()));
        else
            user = User.builder()
                    .userId(1L)
                    .build();
        String s = tokenNonUserProvider.extractUserIdString(forgetPasswordRequest.getToken());
        if("COMPUTER".equalsIgnoreCase(tokenNonUserProvider.extractUserIdString(forgetPasswordRequest.getToken()))) {
            if (user != null) {
                String token = tokenForUserNonLoginProvider.generateToken(user,"type", "forgetPassword");
                return new TokenForUserNonLoginResponse().builder()
                        .token(token)
                        .userId(user.getUserId())
                        .build();
            }else
                throw new UserNotFoundException("with mail " + forgetPasswordRequest.getMail());

        }
        else
            throw new AuthorizationException();
    }

    public ExpiredJwt getExpiredJwt(RequestJwt requestJwt) {
        Optional<ExpiredJwt> jwt = expiredJwtRepo.findByJwt(requestJwt.getJwt());
        return jwt.orElse(null);
    }
}
