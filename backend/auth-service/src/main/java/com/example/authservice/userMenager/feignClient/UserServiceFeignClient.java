package com.example.authservice.userMenager.feignClient;

import com.example.authservice.security.data.entity.ExpiredJwt;
import com.example.authservice.userMenager.api.request.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "USER-SERVICE")
public interface UserServiceFeignClient {
    @GetMapping("/user/mail")
    User getUserByMail(@RequestParam String mail);

    @GetMapping("/user/id")
    User getUserById(@RequestParam Long id);

    @GetMapping("/user/expiredJwt")
    ExpiredJwt getExpiredJwtByJwt(@RequestParam String jwt);
}
