package com.example.demowithtests.web;


import com.example.demowithtests.domain.WorkPlace;
import com.example.demowithtests.dto.workspace.WorkPlaceDto;
import com.example.demowithtests.service.workspaceService.WorkPlaceService;
import com.example.demowithtests.util.config.WorkPlaceConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@Tag(name = "WorkPlace", description = "WorkPlace API")
public class WorkPlaceController {

    private final WorkPlaceService workPlaceService;
    private final WorkPlaceConverter converter;


    @PostMapping("/place")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "This is endpoint to add a new workplayce.", description = "Create request to add a new workplace.", tags = {"WorkPlace"})
    public  WorkPlaceDto saveWorkPlace(@RequestBody @Valid WorkPlaceDto workPlaceDto){
        WorkPlace workPlace = converter.toWorkPlace(workPlaceDto);
        var workPlaceTemp = workPlaceService.create(workPlace);
        var dto = converter.toDto(workPlaceTemp);
        return dto;
    }

    @GetMapping("/place/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "This is endpoint returned a workplayce by his id.",
            description = "Create request to read a workplayce by id", tags = {"WorkPlace"})
    public WorkPlaceDto getWorkPlaceById(@PathVariable Integer id){
        var workPlaceTemp = workPlaceService.getById(id);
        var dto = converter.toDto(workPlaceTemp);
        return dto;
    }
}
