package com.example.demowithtests.util.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class EmployeeExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    protected ResponseEntity<EmployeeErrorResponse> handleEmployeeNotFoundException(EmployeeNotFoundException exception, WebRequest request) {
        String details = extractDetailsFromRequest(request);
        var response = new EmployeeErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Employee not found",
                "Employee details: " + details,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmployeeWasDeletedException.class)
    protected ResponseEntity<EmployeeErrorResponse> handleEmployeeWasDeletedException(EmployeeWasDeletedException exception, WebRequest request) {
        String details = extractDetailsFromRequest(request);
        var response = new EmployeeErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Employee deleted",
                "Employee details: " + details,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    private String extractDetailsFromRequest(WebRequest request) {
        return request.getDescription(false);
    }

    @ExceptionHandler(EmployeePaginationException.class)
    protected ResponseEntity<EmployeeErrorResponse> handleEmployeePaginationException(EmployeePaginationException exception) {
        int page = exception.getPage();
        int size = exception.getSize();

        var errorDetails = new EmployeePaginationErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Employee pagination error",
                exception.getMessage(),
                LocalDateTime.now(),
                page,
                size
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(GenderNotFoundException.class)
    protected ResponseEntity<EmployeeErrorResponse> handleGenderNotFoundException(GenderNotFoundException exception) {
        var response = new EmployeeErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Gender value error",
                "Gender value - " + exception.getMessage() + " not exist",
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}








