package com.example.demowithtests.web;


import com.example.demowithtests.dto.workspace.WorkPlaceDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public interface WorkPlaceController {

    @PostMapping("/place")
    WorkPlaceDto saveWorkPlace(@RequestBody @Valid WorkPlaceDto workPlaceDto);

    @GetMapping("/place/{id}")
    WorkPlaceDto getWorkPlaceById(@PathVariable Integer id);
}
