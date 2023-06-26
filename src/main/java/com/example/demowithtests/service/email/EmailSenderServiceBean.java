package com.example.demowithtests.service.email;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Slf4j
@Service
public class EmailSenderServiceBean implements EmailSenderService {
    private static final String EMAIL_SENDER = "maxim.gorodnyk55@gmail.com";

    private JavaMailSender mailSender;

    @Override
    public void sendEmail(String email, String body, String subject) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(EMAIL_SENDER);
        message.setTo(email);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);
    }
}
