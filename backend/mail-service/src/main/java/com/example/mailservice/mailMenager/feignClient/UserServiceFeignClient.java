package com.example.mailservice.mailMenager.feignClient;

import com.example.mailservice.mailMenager.api.request.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "USER-SERVICE")
public interface UserServiceFeignClient {
    @GetMapping("/user/mail")
    User getUserByMail(@RequestParam String mail);
}
