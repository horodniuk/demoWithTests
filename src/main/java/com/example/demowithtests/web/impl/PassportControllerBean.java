package com.example.demowithtests.web.impl;

import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.dto.ImageReadDto;
import com.example.demowithtests.dto.passport.PassportDto;
import com.example.demowithtests.service.passport.PassportServiceBean;
import com.example.demowithtests.util.converter.PassportConverter;
import com.example.demowithtests.web.PassportController;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
public class PassportControllerBean implements PassportController {
    private final PassportServiceBean passportService;
    private final PassportConverter passportConverter;

    @Override
    public PassportDto savePassport(PassportDto passportDto) {
        Passport passport = passportConverter.toPassport(passportDto);
        final var passportTemp = passportService.create(passport);
        return passportConverter.toDto(passportTemp);
    }

    @Override
    public PassportDto getPassportById(Integer id) {
        Passport passport = passportService.getById(id);
        return passportConverter.toDto(passport);
    }

    @Override
    public PassportDto addPassportImage(ImageReadDto imageReadDto) {
        var imageId = imageReadDto.imageId();
        var passportId = imageReadDto.passportId();
        var passport = passportService.addImage(imageId, passportId);
        return passportConverter.toDto(passport);
    }
}
