package com.example.mailservice.mailMenager.business.service;

import com.example.mailservice.mailMenager.api.request.ContactRequest;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Service
@AllArgsConstructor
public class EmailSenderService {

    private JavaMailSender javaMailSender;

    public boolean sendMail(ContactRequest contactRequest) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo("nxbike.suppor1t@gmail.com");
        mimeMessageHelper.setSubject(contactRequest.getSubject());
        mimeMessageHelper.setText("Wiadomość od: "+contactRequest.getName()+"\n"+"Mail do odesłania wiadomości: "+ contactRequest.getEmailTo()+"\n"+contactRequest.getMessage(), false);
        javaMailSender.send(mimeMessage);
        return true;
    }


}
