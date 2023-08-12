package com.example.demowithtests.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;


public record EmployeeWorkPlaceReadDto(

        @Schema(description = "Employee id", example = "1")
        Integer employeeId,

        @Schema(description = "WorkPlace id", example = "1")
        Integer workPlaceId,

        @Schema(description = "Reservation start time", example = "2023-08-03T01:00:00")
        LocalDateTime reservationStartTime,

        @Schema(description = "Reservation end time", example = "2023-08-03T09:00:00")
        LocalDateTime reservationEndTime
) {
}
