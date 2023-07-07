package com.example.demowithtests.util.annotations.dto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static com.example.demowithtests.util.validation.IsoCountryUtil.isValidISOCountry;

public class CountryRightFormedValidator implements ConstraintValidator<CountryRightFormed, String> {


    @Override
    public boolean isValid(String country, ConstraintValidatorContext constraintValidatorContext) {
        if (country == null) return false;
        return isValidISOCountry(country);
    }
}
