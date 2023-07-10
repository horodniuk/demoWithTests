package com.example.demowithtests.dto.workspace;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Size;

public record WorkPlaceDto(
        @Schema(description = "Workplace ID", example = "1")
        Integer id,

        @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
        @Schema(description = "Workplace name", example = "Office")
        String name,

        @Schema(description = "Determine if the workplace has air conditioning", example = "true")
        Boolean airCondition,

        @Schema(description = "Determine if the workplace has a coffee machine", example = "true")
        Boolean coffeeMachine
) {
    public WorkPlaceDto {
        airCondition = Boolean.TRUE;
        coffeeMachine = Boolean.TRUE;
    }
}
