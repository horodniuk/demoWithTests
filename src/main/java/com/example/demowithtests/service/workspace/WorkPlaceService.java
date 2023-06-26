package com.example.demowithtests.service.workspace;

import com.example.demowithtests.domain.WorkPlace;

public interface WorkPlaceService {

    WorkPlace create(WorkPlace workPlace);

    WorkPlace getById(Integer id);
}
