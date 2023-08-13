package com.example.demowithtests.dto;

import com.example.demowithtests.util.annotations.dto.BlockedEmailDomains;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;
import java.util.Set;

import static io.swagger.v3.oas.annotations.media.Schema.*;


public record EmployeeReadDto (

    @NotNull
    @Schema(name = "Employee ID", example = "1", requiredMode = RequiredMode.REQUIRED)
    Integer id,

    @NotNull(message = "Name may not be null")
    @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
    @Schema(name = "Employee name", description = "Name of an employee.", example = "Billy", requiredMode = RequiredMode.REQUIRED)
    String name,

    @Schema(name = "Employee country", description = "Name of the country.", example = "England", requiredMode = RequiredMode.REQUIRED)
    String country,

    @Email
    @NotNull
    @BlockedEmailDomains(contains = {".com1", ".ru", ".su"})
    @Schema(name = "Employee email", description = "Email address of an employee.", example = "billys@gmail.com", requiredMode = RequiredMode.REQUIRED)
    String email,

    @Schema(name = "Employee addresses", description = "Set of address details for an employee")
    Set<AddressDto> addresses,

    //todo: dfhgjkdfhg Jira - 5544

    Date date
    ) {
        @Builder
        public EmployeeReadDto {
            date = new Date();
        }
}

