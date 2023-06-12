package com.example.demowithtests.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeUpdateMailDto {

    private String oldMail;

    private String newMail;
}
