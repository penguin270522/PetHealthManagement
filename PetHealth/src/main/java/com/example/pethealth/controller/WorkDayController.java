package com.example.pethealth.controller;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.model.WorkDay;
import com.example.pethealth.service.WorkDayService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workday")
public class WorkDayController {

    private final WorkDayService workDayService;

    public WorkDayController(WorkDayService workDayService) {
        this.workDayService = workDayService;
    }
    @GetMapping("/getAllWorkday")
    public BaseDTO getAllWordDay(){
        return workDayService.getAllWorkDay();
    }

    @PostMapping("/createWorkday")
    public BaseDTO createWorkDay(@RequestBody WorkDay workDay){
        return workDayService.createWorkDay(workDay);
    }

    @PutMapping("/updateWorkDay/{id}")
    public BaseDTO updateWorkDay(@PathVariable("id") Long workDayId,
                                 @RequestBody WorkDay request){
        return workDayService.updateWorkDay(request,workDayId);
    }
}
