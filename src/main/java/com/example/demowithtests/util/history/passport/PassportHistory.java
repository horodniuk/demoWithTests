package com.example.demowithtests.util.history.passport;


import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.dto.passport.PassportDto;
import com.example.demowithtests.util.converter.PassportConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@AllArgsConstructor
@Component
public class PassportHistory {
    private final Map<Integer, Map<PassportMethodName, List<PassportDto>>> passportHistory = new LinkedHashMap<>();
    private final PassportConverter passportConverter;

    public Map<Integer, Map<PassportMethodName, List<PassportDto>>> addPassportHistory(
            Integer employeeId, Passport passport, PassportMethodName methodName) {

        PassportDto passportDto = passportConverter.toDto(passport);
        passportHistory.computeIfAbsent(employeeId, history -> new LinkedHashMap<>())
                .computeIfAbsent(methodName, methodHistory -> new ArrayList<>())
                .add(passportDto);
        return passportHistory;
    }

    public Map<Integer, Map<PassportMethodName, List<PassportDto>>> getAllHistory() {
        return Collections.unmodifiableMap(passportHistory);
    }

    public Map<PassportMethodName, List<PassportDto>> getByEmployeeId(Integer employeeId) {
        return passportHistory.get(employeeId);
    }
}
