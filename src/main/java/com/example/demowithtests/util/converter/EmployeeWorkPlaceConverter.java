package com.example.demowithtests.util.converter;


import com.example.demowithtests.domain.EmployeeWorkPlace;
import com.example.demowithtests.dto.EmployeeWorkPlaceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeWorkPlaceConverter {
    EmployeeWorkPlaceConverter INSTANCE = Mappers.getMapper(EmployeeWorkPlaceConverter.class);

    @Mapping(source = "employeeWorkPlacePK.employeeId", target = "employeeId")
    @Mapping(source = "employeeWorkPlacePK.workPlaceId", target = "workPlaceId")
    @Mapping(source = "isActive", target = "isActive")
    EmployeeWorkPlaceDto toDto(EmployeeWorkPlace employeeWorkPlace);

    EmployeeWorkPlace toEmployeeWorkPlace(EmployeeWorkPlaceDto dto);

    List<EmployeeWorkPlaceDto> toDtoList(List<EmployeeWorkPlace> employeeWorkPlaces);
}
