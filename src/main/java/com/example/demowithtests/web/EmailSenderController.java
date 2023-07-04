package com.example.demowithtests.web;


import com.example.demowithtests.service.emailService.EmailMessage;
import com.example.demowithtests.service.emailService.EmailSenderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RestController
@RequestMapping("/api/mail")
@Tag(name = "EmailSender", description = "Mail Sender API")
public class EmailSenderController {
    private final EmailSenderService emailSenderService;

    @PostMapping("/send")
    public void sendEmail(@RequestBody EmailMessage message) {
       emailSenderService.sendEmail(message.getEmail(),
                                    message.getBody(),
                                    message.getSubject());
    }
}
