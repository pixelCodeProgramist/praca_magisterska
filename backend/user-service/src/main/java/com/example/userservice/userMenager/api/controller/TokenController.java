package com.example.userservice.userMenager.api.controller;

import com.example.userservice.UserServiceApplication;
import com.example.userservice.userMenager.business.service.TokenService;
import com.example.userservice.userMenager.business.service.UserService;
import com.example.userservice.userMenager.data.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController()
@RequestMapping("/user/token")
@AllArgsConstructor
@Validated
public class TokenController {

    private TokenService tokenService;
    private UserService userService;

    @GetMapping("/verify")
    public String register(@RequestParam("token") String token, HttpServletResponse httpServletResponse) {
        User user = tokenService.verifyToken(token);
        userService.verifyUser(user);
        String frontSite = UserServiceApplication.FRONT_SITE;
        httpServletResponse.setHeader("Location", frontSite + "login");
        httpServletResponse.setStatus(302);
        int index = frontSite.indexOf("//");
        return "redirect:" + frontSite.substring(index + 2);
    }
}
