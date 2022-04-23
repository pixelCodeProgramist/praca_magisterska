package com.example.mailservice.mailMenager.business.service;

import com.example.mailservice.MailServiceApplication;
import com.example.mailservice.mailMenager.api.request.ContactRequest;
import com.example.mailservice.mailMenager.api.request.RegisterDataRequest;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

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

    public boolean sendMail(RegisterDataRequest registerDataRequest) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(registerDataRequest.getEmailTo());
        mimeMessageHelper.setSubject(registerDataRequest.getSubject());
        mimeMessageHelper.setText(getContentForMailConfirmation(registerDataRequest), true);
        javaMailSender.send(mimeMessage);
        return true;
    }

    private String getContentForMailConfirmation(RegisterDataRequest registerDataRequest) {
        String site = MailServiceApplication.MAIN_SITE + "user/token/verify?token=" + registerDataRequest.getToken();
        return "<div style=\"width: 100%; height: 100%; background-color: #f9f9f9;\">\n" +
                "<div style=\"margin: auto;color: lightgrey;font-size: 25px; text-align: center;padding: 1.5rem 2rem;\">\n" +
                "<span style=\"color: blue;\">Nx</span>Bike\n" +
                "</div>\n" +
                "<div style=\"text-align: center; background-color: white; color: gray;padding: 5rem 1rem;width: 30%;margin: auto;min-width: 400px\">\n" +
                "<h2>Hej " + registerDataRequest.getName() + "</h2>\n" +
                "<div style=\"margin-bottom: 2rem; align-content: center; padding: 0.5rem 2rem; text-align: left;font-size: 18px;\">\n" +
                "<p>\n" +
                "Dziękujemy za zarejestrowanie konta na CinemaCity!\n" +
                "Zanim zaczniemy, musimy potwierdzić, że to Ty.\n" +
                "Kliknij poniżej, aby zweryfikować swój adres e-mail:\n" +
                "</p>\n" +
                "</div>\n" +
                "<div style=\"height: auto; width: auto;\">\n" +
                "<a href=\"" + site + "\" style=\"align-content: center;text-decoration: none;background-color: #007bff; padding: 1rem;color:\n" +
                " white; border-color: #007bff; font-weight: 400;font-size: 1rem;border-radius: 0.25rem;\">\n" +
                "Potwierdz mail\n" +
                "</a>\n" +
                "</div>\n" +
                "</div>\n" +
                "</div>";
    }


}
