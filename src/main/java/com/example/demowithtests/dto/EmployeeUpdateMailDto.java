package com.example.demowithtests.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record EmployeeUpdateMailDto(

        @Schema(name = "Country name", description = "Old email of the employee", example = "new@gmail.com")
        String oldMail,


        @Schema(name = "Country name",description = "New email of the employee", example = "new@gmail.com")
        String newMail
) {
}
