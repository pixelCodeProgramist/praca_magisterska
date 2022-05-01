package com.example.authservice.userMenager.feignClient;

import com.example.authservice.security.api.request.UserByIdRequest;
import com.example.authservice.security.api.request.UserByMailRequest;
import com.example.authservice.userMenager.api.request.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "USER-SERVICE")
public interface UserServiceFeignClient {
    @PostMapping("/user/mail")
    User getUserByMail(@RequestBody UserByMailRequest userByMailRequest);

    @PostMapping("/user/id")
    User getUserById(@RequestBody UserByIdRequest userByIdRequest);
}
