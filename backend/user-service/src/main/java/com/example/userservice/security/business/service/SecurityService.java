package com.example.userservice.security.business.service;

import com.example.userservice.security.api.request.AuthenticationRequest;
import com.example.userservice.security.api.response.AuthenticationResponse;
import com.example.userservice.userMenager.business.exception.user.UserNotFoundException;
import com.example.userservice.userMenager.data.entity.User;
import com.example.userservice.userMenager.data.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.example.userservice.security.data.entity.ExpiredJwt;
import com.example.userservice.security.data.repository.ExpiredJwtRepo;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SecurityService {
    private ExpiredJwtRepo jwtRepo;
    private UserRepo userRepo;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider tokenProvider;

    public AuthenticationResponse logIn(AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getLogin(),
                            authenticationRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Incorrect login or password");
        }

        Optional<User> user = userRepo.findByEmail(authenticationRequest.getLogin());
        if (user.isPresent()) {
            String token = tokenProvider.generateToken(user.get());
            return new AuthenticationResponse().builder()
                    .token(token)
                    .name(user.get().getFirstName() + " " + user.get().getLastName())
                    .mail(user.get().getEmail())
                    .userId(user.get().getUserId())
                    .role(user.get().getRole().getRole())
                    .build();
        } else
            throw new UserNotFoundException(authenticationRequest.getLogin());
    }

    public void logOut(HttpServletRequest httpServletRequest) {
        String jwt = null;
        String header = httpServletRequest.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer "))
            jwt = header.substring(7);

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            Optional<User> user = userRepo.findById(tokenProvider.extractUserId(jwt));

            ExpiredJwt expiredJwt = new ExpiredJwt().builder()
                    //.user(user.get())
                    .jwt(jwt)
                    .build();
            jwtRepo.save(expiredJwt);
        }
    }
}
