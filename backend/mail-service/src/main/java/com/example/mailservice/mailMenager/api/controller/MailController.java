package com.example.mailservice.mailMenager.api.controller;

import com.example.mailservice.mailMenager.api.request.ContactRequest;
import com.example.mailservice.mailMenager.api.request.ForgetPasswordRequest;
import com.example.mailservice.mailMenager.api.request.QRMailRequest;
import com.example.mailservice.mailMenager.api.request.RegisterDataRequest;
import com.example.mailservice.mailMenager.business.service.EmailSenderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;


@RestController()
@RequestMapping("/mail")
@AllArgsConstructor
@Validated
public class MailController {
    private EmailSenderService emailSenderService;

    @PostMapping("/contact")
    public ResponseEntity sendEmailForContact(@Valid @RequestBody ContactRequest contactRequest) throws MessagingException {
        if(emailSenderService.sendMail(contactRequest)) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().body("Błąd w wysyłaniu maila");
    }

    @PostMapping("/registerToken")
    public ResponseEntity sendRegisterToken(@Valid @RequestBody RegisterDataRequest registerDataRequest) throws MessagingException {
        if(emailSenderService.sendMail(registerDataRequest)) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().body("Błąd w wysyłaniu maila");
    }

    @PostMapping("/forgetPassword")
    public ResponseEntity sendTokenForForgetPassword(@Valid @RequestBody ForgetPasswordRequest forgetPasswordRequest) throws MessagingException {
        if(emailSenderService.sendMail(forgetPasswordRequest)) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().body("Błąd w wysyłaniu maila");
    }

    @PostMapping("/sendQrCode")
    public ResponseEntity sendTokenForForgetPassword(@Valid @RequestBody QRMailRequest qrMailRequest) throws MessagingException {
        if(emailSenderService.sendMail(qrMailRequest)) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().body("Błąd w wysyłaniu maila");
    }
}
