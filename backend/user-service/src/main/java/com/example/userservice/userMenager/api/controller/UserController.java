package com.example.userservice.userMenager.api.controller;

import com.example.userservice.userMenager.api.request.RegisterRequest;
import com.example.userservice.userMenager.api.response.ResponseView;
import com.example.userservice.userMenager.api.response.UserView;
import com.example.userservice.userMenager.business.service.RegisterService;
import com.example.userservice.userMenager.business.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController()
@RequestMapping("/user")
@AllArgsConstructor
@Validated
public class UserController {

    private RegisterService registerService;

    private UserService userService;



    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseView register(@Valid @RequestBody RegisterRequest registerRequest) {
        registerService.register(registerRequest);
        return new ResponseView("Użytkownik zarejestrowany poprawnie");
    }

    @GetMapping("/mail")
    public UserView getUserByMail(@RequestParam String mail) {
        return userService.getUserByMail(mail);
    }

    @GetMapping("/id")
    public UserView getUserById(@RequestParam Long id) {
        return userService.getUserById(id);
    }

}
