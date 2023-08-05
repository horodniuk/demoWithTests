package com.example.demowithtests.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record EmployeeWorkPlaceDto(
        @Schema(description = "Employee id", example = "1")
        Integer employeeId,

        @Schema(description = "WorkPlace id", example = "1")
        Integer workPlaceId,

        @Schema(description = "Is WorkPlace use in employee", example = "1")
        Boolean isActive,

        @Schema(description = "Reservation start time")
        LocalDateTime reservationStartTime,

        @Schema(description = "Reservation end time")
        LocalDateTime reservationEndTime,

        @Schema(description = "Created on")
        LocalDateTime createdOn
) {
}
