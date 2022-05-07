package com.karol.offerservice.offerMenager.feignClient;

import com.karol.offerservice.business.request.User;
import com.karol.offerservice.business.request.UserByIdRequest;
import com.karol.offerservice.business.request.UserByMailRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "USER-SERVICE")
public interface UserServiceFeignClient {
    @PostMapping("/user/mail")
    User getUserByMail(@RequestBody UserByMailRequest userByMailRequest);

    @PostMapping("/user/id")
    User getUserById(@RequestBody UserByIdRequest userByIdRequest);
}

