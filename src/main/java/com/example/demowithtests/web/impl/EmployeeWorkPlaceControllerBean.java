package com.example.demowithtests.web.impl;

import com.example.demowithtests.domain.EmployeeWorkPlace;
import com.example.demowithtests.dto.EmployeeWorkPlaceDto;
import com.example.demowithtests.dto.EmployeeWorkPlaceReadDto;
import com.example.demowithtests.service.employee_workplace.EmployeeWorkPlaceService;
import com.example.demowithtests.util.converter.EmployeeWorkPlaceConverter;
import com.example.demowithtests.web.EmployeeWorkPlaceController;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
public class EmployeeWorkPlaceControllerBean implements EmployeeWorkPlaceController {
  private final EmployeeWorkPlaceService employeeWorkPlaceService;
  private final EmployeeWorkPlaceConverter converter;

  @Override
  public List<EmployeeWorkPlaceDto> getActiveEmployeeWorkPlaces(@PathVariable Integer id) {
    List<EmployeeWorkPlace> activeWorkPlaces = employeeWorkPlaceService.findByEmployeeId(id);
    return converter.toDtoList(activeWorkPlaces);
  }

  @Override
  public EmployeeWorkPlaceDto deactivatePlaceToEmployee(@RequestBody EmployeeWorkPlaceReadDto employeeWorkPlaceReadDto) {
    Integer employeeId = employeeWorkPlaceReadDto.employeeId();
    Integer workPlaceId = employeeWorkPlaceReadDto.workPlaceId();
    var employeeWorkPlace = employeeWorkPlaceService.deactivateEmployeeWorkPlace(employeeId, workPlaceId);
    return converter.toDto(employeeWorkPlace);
  }

  @Override
  public EmployeeWorkPlaceDto addWorkPlaceToEmployee(@RequestBody EmployeeWorkPlaceReadDto employeeWorkPlaceReadDto) {
    Integer employeeId = employeeWorkPlaceReadDto.employeeId();
    Integer workPlaceId = employeeWorkPlaceReadDto.workPlaceId();
    LocalDateTime reservationStartTime = employeeWorkPlaceReadDto.reservationStartTime();
    LocalDateTime reservationEndTime = employeeWorkPlaceReadDto.reservationEndTime();
    var employeeWorkPlace = employeeWorkPlaceService.addWorkPlaceToEmployee(employeeId, workPlaceId, reservationStartTime, reservationEndTime);
    return converter.toDto(employeeWorkPlace);
  }

}
