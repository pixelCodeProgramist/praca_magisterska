package com.example.authservice.security.api.controller;

import com.example.authservice.security.api.request.AuthenticationRequest;
import com.example.authservice.security.api.request.ForgetPasswordRequest;
import com.example.authservice.security.api.request.RequestJwt;
import com.example.authservice.security.api.response.AuthenticationResponse;
import com.example.authservice.security.api.response.TokenForUserNonLoginResponse;
import com.example.authservice.security.business.service.SecurityService;
import com.example.authservice.userMenager.api.request.User;
import com.example.authservice.userMenager.data.entity.ExpiredJwt;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/auth/user")
@AllArgsConstructor
public class SecurityController {
    private SecurityService securityService;

    @GetMapping("/logout")
    public void logoutUser(HttpServletRequest httpServletRequest)
    {
        securityService.logOut(httpServletRequest, false);
    }

    @PostMapping("/login")
    public AuthenticationResponse loginUser(@Valid @RequestBody AuthenticationRequest authenticationRequest)
    {
        return securityService.logIn(authenticationRequest);
    }

    @PostMapping("/forgetPassword")
    public TokenForUserNonLoginResponse forgetPassword(@Valid @RequestBody ForgetPasswordRequest forgetPasswordRequest)
    {
        return securityService.generateTokenNonLoginForUser(forgetPasswordRequest, false);
    }

    @PostMapping("/expiredJwt")
    public ExpiredJwt expiredJwt(@RequestBody(required = false) RequestJwt requestJwt) {
        if(requestJwt != null) return securityService.getExpiredJwt(requestJwt);
        else return null;
    }
}
