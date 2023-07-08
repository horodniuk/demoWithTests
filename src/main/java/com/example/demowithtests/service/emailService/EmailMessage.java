package com.example.demowithtests.service.emailService;

import lombok.Data;

@Data
public class EmailMessage {
        private String email;
        private String body;
        private String subject;
}
