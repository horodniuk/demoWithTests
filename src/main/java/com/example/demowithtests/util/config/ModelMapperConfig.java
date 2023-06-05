package com.example.demowithtests.util.config;


import com.example.demowithtests.domain.Address;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.AddressDto;
import com.example.demowithtests.dto.EmployeeDto;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper getModelMapper() {

        ModelMapper modelMapper = new ModelMapper();

        // Configure mappings from Employee to EmployeeDto
        modelMapper.createTypeMap(Employee.class, EmployeeDto.class)
                .addMappings(mapper -> {
                    mapper.map(src -> src.getAddresses(), EmployeeDto::setAddresses);
                });

        // Configure mappings from EmployeeDto to Employee
        modelMapper.createTypeMap(EmployeeDto.class, Employee.class)
                .addMappings(mapper -> {
                    mapper.map(src -> src.getAddresses(), Employee::setAddresses);
                });

        // Configure mappings from Address to AddressDto
        modelMapper.createTypeMap(Address.class, AddressDto.class)
                .addMapping(Address::getAddressHasActive, AddressDto::setAddressHasActive);

        return modelMapper;
    }

}
