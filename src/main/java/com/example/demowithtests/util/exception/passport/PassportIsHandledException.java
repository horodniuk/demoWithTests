package com.example.demowithtests.util.exception.passport;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PassportIsHandledException extends RuntimeException {
    public PassportIsHandledException(String message) {
        super(message);
    }
}
