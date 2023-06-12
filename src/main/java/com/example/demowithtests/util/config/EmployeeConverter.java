package com.example.demowithtests.util.config;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeReadDto;
import com.example.demowithtests.dto.EmployeeUpdateMailDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeConverter {

    EmployeeDto toDto(Employee employee);

    EmployeeReadDto toReadDto(Employee employee);

    Employee fromDto(EmployeeDto dto);

    List<EmployeeDto> toDtoList(List<Employee> employees);

    EmployeeUpdateMailDto toUpdateMailDto(String oldMail, String newMail);

}
