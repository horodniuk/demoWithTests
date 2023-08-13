package com.example.demowithtests.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeWorkPlacePK implements Serializable {
    @Column(name = "employee_id")
    private Integer employeeId;

    @Column(name = "workplace_id")
    private Integer workPlaceId;
}
