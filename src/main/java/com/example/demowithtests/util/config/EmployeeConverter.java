package com.example.demowithtests.util.config;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeReadDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EmployeeConverter {

    private final ModelMapper modelMapper;

    public EmployeeConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public EmployeeDto toDto(Employee entity) {
        return modelMapper.map(entity, EmployeeDto.class);
    }

    public EmployeeReadDto toReadDto(Employee entity) {
        return modelMapper.map(entity, EmployeeReadDto.class);
    }


    public Employee fromDto(EmployeeDto dto) {
        return modelMapper.map(dto, Employee.class);
    }
}
