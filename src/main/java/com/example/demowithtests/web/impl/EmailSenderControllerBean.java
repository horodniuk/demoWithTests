package com.example.demowithtests.web.impl;


import com.example.demowithtests.service.emailService.EmailMessage;
import com.example.demowithtests.service.emailService.EmailSenderService;
import com.example.demowithtests.web.EmailSenderController;
import com.example.demowithtests.web.swagger.EmailSenderControllerSwagger;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RestController
public class EmailSenderControllerBean implements EmailSenderController, EmailSenderControllerSwagger {
    private final EmailSenderService emailSenderService;

    @Override
    public void sendEmail(@RequestBody EmailMessage message) {
       emailSenderService.sendEmail(message.getEmail(),
                                    message.getBody(),
                                    message.getSubject());
    }
}
