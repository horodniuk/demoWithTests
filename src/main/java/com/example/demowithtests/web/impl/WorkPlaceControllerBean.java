package com.example.demowithtests.web.impl;


import com.example.demowithtests.domain.WorkPlace;
import com.example.demowithtests.dto.workspace.WorkPlaceDto;
import com.example.demowithtests.service.workspaceService.WorkPlaceService;
import com.example.demowithtests.util.converter.WorkPlaceConverter;
import com.example.demowithtests.web.WorkPlaceController;
import com.example.demowithtests.web.swagger.WorkPlaceControllerSwagger;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class WorkPlaceControllerBean implements WorkPlaceController, WorkPlaceControllerSwagger {

    private final WorkPlaceService workPlaceService;
    private final WorkPlaceConverter converter;

    @Override
    public WorkPlaceDto saveWorkPlace(WorkPlaceDto workPlaceDto) {
        WorkPlace workPlace = converter.toWorkPlace(workPlaceDto);
        var workPlaceTemp = workPlaceService.create(workPlace);
        return converter.toDto(workPlaceTemp);
    }

    @Override
    public WorkPlaceDto getWorkPlaceById(Integer id) {
        var workPlaceTemp = workPlaceService.getById(id);
        return converter.toDto(workPlaceTemp);
    }
}
