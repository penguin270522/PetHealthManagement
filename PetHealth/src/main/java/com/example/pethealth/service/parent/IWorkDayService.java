package com.example.pethealth.service.parent;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.model.WorkDay;

public interface IWorkDayService {

    BaseDTO createWorkDay(WorkDay workDay);
    BaseDTO getAllWorkDay();

    BaseDTO updateWorkDay(WorkDay workDay, Long workDayId);
}
