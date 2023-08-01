package com.example.demowithtests.util.converter;


import com.example.demowithtests.domain.Image;
import com.example.demowithtests.dto.ImageDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ImageConverter {

    ImageConverter INSTANCE = Mappers.getMapper(ImageConverter.class);

    Image toImage(ImageDto dto);

    ImageDto toDto(Image image);

}
