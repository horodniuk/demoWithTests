package com.example.demowithtests.util.converter;


import com.example.demowithtests.domain.Image;
import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.dto.ImageDto;
import com.example.demowithtests.dto.passport.PassportDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {ImageConverter.class})
public interface PassportConverter {
    PassportConverter INSTANCE = Mappers.getMapper(PassportConverter.class);

    @Mapping(source = "passport.image", target = "imageDto")
    PassportDto toDto (Passport passport);

    Passport toPassport(PassportDto dto);

}
