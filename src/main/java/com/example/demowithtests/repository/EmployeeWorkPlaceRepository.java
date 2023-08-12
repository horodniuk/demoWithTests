package com.example.demowithtests.repository;


import com.example.demowithtests.domain.EmployeeWorkPlace;
import com.example.demowithtests.domain.EmployeeWorkPlacePK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EmployeeWorkPlaceRepository extends JpaRepository<EmployeeWorkPlace, EmployeeWorkPlacePK> {
    List<EmployeeWorkPlace> findByEmployeeWorkPlacePK_EmployeeIdAndIsActive(Integer employeeId, Boolean isActive);

    List<EmployeeWorkPlace> findByWorkPlaceIdAndReservationEndTimeAfterAndReservationStartTimeBeforeAndIsActive(Integer workPlaceId, LocalDateTime startTime, LocalDateTime endTime, Boolean isActive);

    Optional<EmployeeWorkPlace> findByEmployeeWorkPlacePK_EmployeeIdAndEmployeeWorkPlacePK_WorkPlaceIdAndIsActive(Integer employeeId, Integer workPlaceId, Boolean aTrue);
}
