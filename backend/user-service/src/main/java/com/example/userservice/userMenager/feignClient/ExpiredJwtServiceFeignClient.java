package com.example.userservice.userMenager.feignClient;

import com.example.userservice.business.request.RequestJwt;
import com.example.userservice.business.response.ExpiredJwtResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "AUTH-SERVICE")
public interface ExpiredJwtServiceFeignClient {
    @GetMapping("/auth/user/expiredJwt")
    ExpiredJwtResponse getExpiredJwtByJwt(@RequestParam RequestJwt jwt);
}
