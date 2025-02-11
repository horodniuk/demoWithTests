package com.example.demowithtests.web;

import com.example.demowithtests.dto.ImageReadDto;
import com.example.demowithtests.dto.passport.PassportDto;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public interface PassportController {
    @PostMapping("/passport")
    PassportDto savePassport(@RequestBody @Valid PassportDto passportDto);

    @GetMapping("/passport/{id}")
    PassportDto getPassportById(@PathVariable Integer id);

    @PatchMapping("/passport/image")
    PassportDto addPassportImage(@RequestBody ImageReadDto imageReadDto);

}
