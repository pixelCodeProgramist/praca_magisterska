package com.example.mailservice.mailMenager.feignClient;

import com.example.mailservice.mailMenager.api.request.User;
import com.example.mailservice.mailMenager.api.request.UserByMailRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "USER-SERVICE")
public interface UserServiceFeignClient {
    @PostMapping("/user/mail")
    User getUserByMail(@RequestBody UserByMailRequest userByMailRequest);
}
