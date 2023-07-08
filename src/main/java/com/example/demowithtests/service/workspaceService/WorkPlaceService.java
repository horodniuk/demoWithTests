package com.example.demowithtests.service.workspaceService;

import com.example.demowithtests.domain.WorkPlace;

public interface WorkPlaceService {

    WorkPlace create(WorkPlace workPlace);

    WorkPlace getById(Integer id);
}
