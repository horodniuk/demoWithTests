package com.example.demowithtests.util.annotations.dto;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

import static com.example.demowithtests.util.loader.DataLoader.loadDataFromResource;


@Slf4j
public class BlockedEmailDomainsValidator implements ConstraintValidator<BlockedEmailDomains, String> {
    private static final String REQUIRED_POSTAL_SYMBOL = "@";
    private String[] domainsFirstLevel;
    private List<String> domainsSecondLevel;

    @Override
    public void initialize(BlockedEmailDomains constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        domainsFirstLevel = constraintAnnotation.contains();
        String pathDomainsSecondLevel = constraintAnnotation.blockedEmailDomainsList();
        domainsSecondLevel = loadDataFromResource(pathDomainsSecondLevel);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (email == null) return false;

        String[] emailParts = email.split(REQUIRED_POSTAL_SYMBOL);

        if (emailParts.length != 2) {
            return false;
        }

        String domain = emailParts[1];

        boolean isValidDomainFirstLevel = Arrays.stream(domainsFirstLevel).noneMatch(domain::endsWith);

        boolean isValidDomainSecondLevel = domainsSecondLevel.stream().noneMatch(domain::startsWith);

        return isValidDomainFirstLevel && isValidDomainSecondLevel;
    }


}
