package com.example.mailservice.mailMenager.api.controller;

import com.example.mailservice.mailMenager.api.request.ContactRequest;
import com.example.mailservice.mailMenager.business.service.EmailSenderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;


@RestController()
@RequestMapping("/mail")
@AllArgsConstructor
@Validated
public class MailController {
    private EmailSenderService emailSenderService;

    @PostMapping("/contact")
    public ResponseEntity sendEmailForContact(@RequestBody ContactRequest contactRequest) throws MessagingException {
        if(emailSenderService.sendMail(contactRequest)) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().body("Błąd w wysyłaniu maila");
    }
}
