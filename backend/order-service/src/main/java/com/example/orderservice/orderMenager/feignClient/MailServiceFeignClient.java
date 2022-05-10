package com.example.orderservice.orderMenager.feignClient;

import com.example.orderservice.orderMenager.api.request.ContactRequest;
import com.example.orderservice.orderMenager.api.request.QRMailRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "MAIL-SERVICE")
public interface MailServiceFeignClient {
    @PostMapping("mail/sendQrCode")
    ResponseEntity sendQrCode(@Valid @RequestBody QRMailRequest qrMailRequest);

    @PostMapping("mail/contact")
    ResponseEntity sendEmailForContact(@Valid @RequestBody ContactRequest contactRequest);

}
