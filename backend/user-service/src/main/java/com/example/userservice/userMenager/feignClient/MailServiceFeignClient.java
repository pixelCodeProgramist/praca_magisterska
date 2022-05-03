package com.example.userservice.userMenager.feignClient;

import com.example.userservice.userMenager.api.request.RegisterDataRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "MAIL-SERVICE")
public interface MailServiceFeignClient {
    @GetMapping("/mail/registerToken")
    void sendMail(RegisterDataRequest registerDataRequest);
}
