package com.example.demowithtests.util.config;

import com.example.demowithtests.domain.WorkPlace;
import com.example.demowithtests.dto.workspace.WorkPlaceDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WorkPlaceConverter {

    WorkPlaceConverter INSTANCE = Mappers.getMapper(WorkPlaceConverter.class);

    WorkPlaceDto toDto( WorkPlace workPlace);

    WorkPlace toWorkPlace(WorkPlaceDto dto);

}
