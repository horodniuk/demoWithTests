package com.example.demowithtests.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmployeeUpdateMailDto {

    public String oldMail;

    public String newMail;
}
