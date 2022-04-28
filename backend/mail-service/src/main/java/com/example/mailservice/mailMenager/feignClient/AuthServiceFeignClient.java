package com.example.mailservice.mailMenager.feignClient;

import com.example.mailservice.mailMenager.api.dto.TokenForUserNonLoginResponse;
import com.example.mailservice.mailMenager.api.request.ForgetPasswordRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "AUTH-SERVICE")
public interface AuthServiceFeignClient {
    @PostMapping("/auth/user/forgetPassword")
    TokenForUserNonLoginResponse forgetPassword(@RequestBody ForgetPasswordRequest forgetPasswordRequest);
}
