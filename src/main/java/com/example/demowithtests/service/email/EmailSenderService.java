package com.example.demowithtests.service.email;

import org.springframework.mail.MailException;

public interface EmailSenderService {
    void sendEmail(String toMail, String subject, String body) throws MailException;
}
