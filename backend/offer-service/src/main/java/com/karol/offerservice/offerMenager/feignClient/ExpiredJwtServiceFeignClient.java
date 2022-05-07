package com.karol.offerservice.offerMenager.feignClient;


import com.karol.offerservice.business.request.RequestJwt;
import com.karol.offerservice.business.response.ExpiredJwtResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "AUTH-SERVICE")
public interface ExpiredJwtServiceFeignClient {
    @PostMapping("/auth/user/expiredJwt")
    ExpiredJwtResponse getExpiredJwtByJwt(@RequestBody RequestJwt jwt);
}
