package com.example.demowithtests.dto.workspace;

public record WorkPlaceDto(
        Integer id,
        String name,
        Boolean airCondition,
        Boolean coffeeMachine) {
    public WorkPlaceDto {
        airCondition = Boolean.TRUE;
        coffeeMachine = Boolean.TRUE;
    }
}
