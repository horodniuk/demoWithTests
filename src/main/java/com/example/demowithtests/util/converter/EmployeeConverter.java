package com.example.demowithtests.util.converter;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeReadDto;
import com.example.demowithtests.dto.EmployeeUpdateMailDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeConverter {

    EmployeeConverter INSTANCE = Mappers.getMapper(EmployeeConverter.class);

    @Mapping(source = "passport.image", target = "passport.imageDto")
    EmployeeDto toDto(Employee employee);

    EmployeeReadDto toReadDto(Employee employee);

    Employee toEmployee(EmployeeDto dto);

    List<EmployeeDto> toDtoList(List<Employee> employees);

    EmployeeUpdateMailDto toUpdateMailDto(String oldMail, String newMail);

}
