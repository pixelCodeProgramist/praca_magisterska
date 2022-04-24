package com.example.authservice.security.api.controller;

import com.example.authservice.security.api.request.AuthenticationRequest;
import com.example.authservice.security.api.response.AuthenticationResponse;
import com.example.authservice.security.business.service.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth/user")
@AllArgsConstructor
public class SecurityController {
    private SecurityService securityService;

    @GetMapping("/logout")
    public void logoutUser(HttpServletRequest httpServletRequest)
    {
        securityService.logOut(httpServletRequest);
    }

    @PostMapping("/login")
    public AuthenticationResponse loginUser(@Valid @RequestBody AuthenticationRequest authenticationRequest)
    {
        return securityService.logIn(authenticationRequest);
    }
}
