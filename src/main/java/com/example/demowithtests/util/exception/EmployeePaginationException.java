package com.example.demowithtests.util.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
@Getter
public class EmployeePaginationException extends RuntimeException {
    private int page;
    private int size;


    public EmployeePaginationException(String message, int page, int size) {
        super(message);
        this.page = page;
        this.size = size;
    }
}
