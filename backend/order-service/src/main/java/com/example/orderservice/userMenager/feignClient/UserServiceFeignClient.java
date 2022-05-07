package com.example.orderservice.userMenager.feignClient;

import com.example.orderservice.security.business.request.UserByIdRequest;
import com.example.orderservice.security.business.request.UserByMailRequest;
import com.example.orderservice.userMenager.api.request.User;
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
