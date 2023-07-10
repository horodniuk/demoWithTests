package com.example.demowithtests.web.swagger;


import com.example.demowithtests.service.emailService.EmailMessage;
import com.example.demowithtests.service.emailService.EmailSenderService;
import com.example.demowithtests.web.EmailSenderController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@Tag(name = "EmailSender", description = "Mail Sender API")
public interface EmailSenderControllerSwagger {
    @Operation(summary = "Send an email.",
            description = "Create request to send an email.", tags = {"EmailSender"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request")})
    void sendEmail(EmailMessage message);
}
