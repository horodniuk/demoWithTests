package com.example.demowithtests.service.employee_workplace;

import com.example.demowithtests.domain.EmployeeWorkPlace;
import com.example.demowithtests.dto.EmployeeWorkPlaceDto;

import java.time.LocalDateTime;
import java.util.List;

public interface EmployeeWorkPlaceService {
    EmployeeWorkPlace save(EmployeeWorkPlace employeeWorkPlace);

    EmployeeWorkPlace deactivateEmployeeWorkPlace(Integer employeeId, Integer workPlaceId);

    List<EmployeeWorkPlace> findByEmployeeId(Integer employeeId);

    boolean checkLimitWorkPlace(Integer employeeId);

    EmployeeWorkPlace addWorkPlaceToEmployee(Integer employeeId, Integer workPlaceId, LocalDateTime reservationStartTime, LocalDateTime reservationEndTime);
}
