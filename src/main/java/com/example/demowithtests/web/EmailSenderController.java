package com.example.demowithtests.web;


import com.example.demowithtests.service.email.EmailSenderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@AllArgsConstructor
@RestController
@RequestMapping("/api/mail")
@Tag(name = "EmailSender", description = "Mail Sender API")
public class EmailSenderController {
    private final EmailSenderService emailSenderService;

    @PostMapping("/send")
    public void sendEmail(@RequestParam String email,
                          @RequestParam String body,
                          @RequestParam String subject) {
      emailSenderService.sendEmail(email, body, subject);
    }
}
