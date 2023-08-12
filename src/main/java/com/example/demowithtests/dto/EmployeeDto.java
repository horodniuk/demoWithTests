package com.example.demowithtests.dto;

import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.dto.passport.PassportDto;
import com.example.demowithtests.util.annotations.dto.BlockedEmailDomains;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Set;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode;


public record EmployeeDto (


    @Schema(name = "Employee ID", example = "1")
    Integer id,

    @NotNull
    @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
    @Schema(name = "Employee ID", description = "Name of an employee.", example = "Billy", requiredMode = RequiredMode.REQUIRED)
    String name,

    @Schema(name = "Employee country", description = "Name of the country.", example = "England", requiredMode = RequiredMode.REQUIRED)
    String country,

    @Email
    @NotNull
    @BlockedEmailDomains(contains = {".com1", ".ru", ".su"})
    @Schema(name = "Employee email", description = "Email address of an employee.", example = "billys@gmail.com", requiredMode = RequiredMode.REQUIRED)
    String email,

    @Schema(name = "Employee start date", description = "Start date of the employee")
    Instant startDate,

    @Schema(name = "Employee gender", description = "Gender of an employee.")
    Gender gender,

    @Valid
    @Schema(name = "addresses", description = "Set of address details for an employee")
    Set<AddressDto> addresses,

    @Schema(name = "Employee passport", description = "Passport of an employee.")
    PassportDto passport
    ){
        @Builder
        public EmployeeDto {
            startDate = Instant.now();
        }
    }
