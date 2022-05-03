package com.example.mailservice.mailMenager.business.service;

import com.example.mailservice.MailServiceApplication;
import com.example.mailservice.mailMenager.api.dto.TokenForUserNonLoginResponse;
import com.example.mailservice.mailMenager.api.request.*;
import com.example.mailservice.mailMenager.feignClient.AuthServiceFeignClient;
import com.example.mailservice.mailMenager.feignClient.UserServiceFeignClient;
import com.example.mailservice.tokenMenager.request.JwtTokenNonUserProvider;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.file.attribute.UserPrincipalNotFoundException;

@Service
@AllArgsConstructor
public class EmailSenderService {

    private JavaMailSender javaMailSender;
    private UserServiceFeignClient userServiceFeignClient;
    private AuthServiceFeignClient authServiceFeignClient;

    private JwtTokenNonUserProvider tokenNonUserProvider;

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

    public boolean sendMail(ForgetPasswordRequest forgetPasswordRequest) throws MessagingException {
        User user = userServiceFeignClient.getUserByMail(new UserByMailRequest(forgetPasswordRequest.getMail(), tokenNonUserProvider.generateToken()));
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(forgetPasswordRequest.getMail());
        mimeMessageHelper.setSubject("Zmiana hasła do konta na NxBike");
        mimeMessageHelper.setText(getContentForMailConfirmation(forgetPasswordRequest, user), true);
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
                "Dziękujemy za zarejestrowanie konta na NxBike!\n" +
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

    private String getContentForMailConfirmation(ForgetPasswordRequest forgetPasswordRequest, User user) {

        TokenForUserNonLoginResponse tokenForUserNonLoginResponse = authServiceFeignClient.forgetPassword(
                new ForgetPasswordFeignClientRequest(forgetPasswordRequest.getMail(),
                        tokenNonUserProvider.generateToken()));

        String site = MailServiceApplication.FRONT_SITE + "forget_password_mail_response?token=" + tokenForUserNonLoginResponse.getToken();
        return "<div style=\"width: 100%; height: 100%; background-color: #f9f9f9;\">\n" +
                "<div style=\"margin: auto;color: lightgrey;font-size: 25px; text-align: center;padding: 1.5rem 2rem;\">\n" +
                "<span style=\"color: blue;\">Nx</span>Bike\n" +
                "</div>\n" +
                "<div style=\"text-align: center; background-color: white; color: gray;padding: 5rem 1rem;width: 30%;margin: auto;min-width: 400px\">\n" +
                "<h2>Hej, " + user.getFirstName() + " " + user.getLastName() +"</h2>\n" +
                "<div style=\"margin-bottom: 2rem; align-content: center; padding: 0.5rem 2rem; text-align: left;font-size: 18px;\">\n" +
                "<p>\n" +
                "Dostaliśmy informację o chęci zmiany hasła do konta na NxBike!\n" +
                "Zanim zaczniemy, chcemy się upewnić, że rzeczywiście chcesz zmienić hasło.\n" +
                "Kliknij poniżej, aby dokonać zmiany hasła. Jeśli to nie ty prosiłeś o zmianę hasła prosimy zignoruj tą wiadomość\n" +
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
