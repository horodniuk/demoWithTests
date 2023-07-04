package com.example.demowithtests.util.annotations.dto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.example.demowithtests.util.validation.IsoUtil.isValidISOCountry;

public class CountryRightFormedValidator implements ConstraintValidator<CountryRightFormed, String> {


    @Override
    public boolean isValid(String country, ConstraintValidatorContext constraintValidatorContext) {
        if (country == null) return false;
        return isValidISOCountry(country);
    }
}
