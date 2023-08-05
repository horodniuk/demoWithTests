package com.example.demowithtests.web;


import com.example.demowithtests.dto.EmployeeWorkPlaceDto;
import com.example.demowithtests.dto.EmployeeWorkPlaceReadDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee-workplaces")
public interface EmployeeWorkPlaceController {

    @GetMapping("/{id}")
    List<EmployeeWorkPlaceDto> getActiveEmployeeWorkPlaces(@PathVariable Integer id);

    @PatchMapping("/deactivate")
    EmployeeWorkPlaceDto deactivatePlaceToEmployee(@RequestBody EmployeeWorkPlaceReadDto employeeWorkPlaceReadDto);

    @PostMapping("/add")
    EmployeeWorkPlaceDto addWorkPlaceToEmployee(@RequestBody EmployeeWorkPlaceReadDto employeeWorkPlaceReadDto);
}
