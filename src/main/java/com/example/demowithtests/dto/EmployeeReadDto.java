package com.example.demowithtests.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;



public record EmployeeReadDto (

    Integer id,

    @NotNull(message = "Name may not be null")
    @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
    @Schema(description = "Name of an employee.", example = "Billy", required = true)
    String name,

    String country,

    @Email
    @NotNull
    String email,

    Set<AddressDto> addresses,

    //todo: dfhgjkdfhg Jira - 5544
    Date date
    ) {
        @Builder
        public EmployeeReadDto {
            date = new Date();
        }
}

