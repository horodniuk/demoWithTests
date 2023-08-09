package com.example.demowithtests.web;

import com.example.demowithtests.domain.Passport;
import com.example.demowithtests.dto.passport.PassportDto;
import com.example.demowithtests.util.history.passport.PassportMethodName;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@RequestMapping(value = "/api/history")
public interface HistoryController {

    @GetMapping("/passport/{id}")
    Map<PassportMethodName, List<PassportDto>> getPassportHistoryByEmployeeId(@PathVariable Integer id);

    @GetMapping("/passport")
    Map<Integer, Map<PassportMethodName, List<PassportDto>>> getAllHistory();
}
