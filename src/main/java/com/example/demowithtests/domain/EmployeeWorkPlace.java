package com.example.demowithtests.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "employee_workplace")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeWorkPlace {
    @EmbeddedId
    private EmployeeWorkPlacePK employeeWorkPlacePK;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("employeeId")
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("workPlaceId")
    @JoinColumn(name = "workplace_id")
    private WorkPlace workPlace;

    @Builder.Default
    @Column(name = "is_active")
    private Boolean isActive = Boolean.TRUE;

    @Column(name = "reservation_start_time")
    private LocalDateTime reservationStartTime;

    @Column(name = "reservation_end_time")
    private LocalDateTime reservationEndTime;

    @Builder.Default
    @Column(name = "created_on")
    private LocalDateTime createdOn = LocalDateTime.now();
}
