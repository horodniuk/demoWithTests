package com.example.demowithtests.web.impl;

import com.example.demowithtests.dto.passport.PassportDto;
import com.example.demowithtests.util.history.passport.PassportHistory;
import com.example.demowithtests.util.history.passport.PassportMethodName;
import com.example.demowithtests.web.HistoryController;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@AllArgsConstructor
@RestController
public class HistoryControllerBean implements HistoryController {
    private final PassportHistory passportHistory;

    @Override
    public Map<PassportMethodName, List<PassportDto>> getPassportHistoryByEmployeeId(Integer id) {
        return passportHistory.getByEmployeeId(id);
    }

    @Override
    public Map<Integer, Map<PassportMethodName, List<PassportDto>>> getAllHistory() {
        return passportHistory.getAllHistory();
    }
}
