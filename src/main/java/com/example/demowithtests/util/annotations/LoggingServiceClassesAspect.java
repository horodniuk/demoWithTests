package com.example.demowithtests.util.annotations;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Log4j2
@Aspect
@Component
public class LoggingServiceClassesAspect {

    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";

    @Pointcut("execution(public * com.example.demowithtests.service.EmployeeServiceBean.*(..))")
    public void callAtMyServicesPublicMethods() {
    }

    @Before("callAtMyServicesPublicMethods()")
    public void logBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        var args = joinPoint.getArgs();
        if (args.length > 0) {
            log.debug(ANSI_BLUE + "Service: " + methodName + " - start. Args count - {}" + ANSI_RESET, args.length);
        } else {
            log.debug(ANSI_BLUE + "Service: " + methodName + " - start." + ANSI_RESET);
        }
    }

   @AfterReturning(value = "callAtMyServicesPublicMethods()", returning = "returningValue")
    public void logAfter(JoinPoint joinPoint, Object returningValue) {
        String methodName = joinPoint.getSignature().toShortString();
        Optional<String> outputValue = getOutputValue(returningValue);
        logMessage(methodName, outputValue);
    }

    private Optional<String> getOutputValue(Object returningValue) {
        return Optional.ofNullable(returningValue)
                .map(value -> value instanceof Collection
                        ? "Collection size - " + ((Collection<?>) value).size()
                        : value instanceof byte[]
                        ? "File as byte[]"
                        : value.toString());
    }

    private void logMessage(String methodName, Optional<String> outputValue) {
        if (outputValue.isPresent()) {
            log.debug(ANSI_BLUE + "Service: " + methodName + " - end. Returns - {}" + ANSI_RESET, outputValue);
        } else {
            log.debug(ANSI_BLUE + "Service: " + methodName + " - end." + ANSI_RESET);
        }
    }

}
