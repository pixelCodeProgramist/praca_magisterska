package com.example.userservice.userMenager.feignClient;

import com.example.userservice.userMenager.api.request.RegisterDataRequest;
import com.example.userservice.userMenager.api.response.BranchView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "GENERAL-INFORMATION-SERVICE")
public interface GeneralInformationServiceFeignClient {
    @GetMapping("/general-information/branch")
    BranchView getBranch(@RequestParam Long id);
}
