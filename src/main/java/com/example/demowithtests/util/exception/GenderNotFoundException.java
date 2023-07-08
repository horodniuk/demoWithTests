package com.example.demowithtests.util.exception;

public class GenderNotFoundException extends IllegalArgumentException {
    public GenderNotFoundException(String s) {
        super(s);
    }
}
