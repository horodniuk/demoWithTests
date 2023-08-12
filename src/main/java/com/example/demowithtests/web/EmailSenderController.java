package com.example.demowithtests.web;


import com.example.demowithtests.service.emailService.EmailMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/mail")
public interface EmailSenderController {

    @PostMapping("/send")
    void sendEmail(@RequestBody EmailMessage message);
}
