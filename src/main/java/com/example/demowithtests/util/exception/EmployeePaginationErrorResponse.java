package com.example.demowithtests.util.exception;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class EmployeePaginationErrorResponse extends EmployeeErrorResponse {
    private final int page;
    private final int size;

    public EmployeePaginationErrorResponse(int errorCode, String message, String details, LocalDateTime timestamp, int page, int size) {
        super(errorCode, message, details, timestamp);
        this.page = page;
        this.size = size;
    }
}
