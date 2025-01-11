package com.example.pethealth.service;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.exception.BadRequestException;
import com.example.pethealth.model.WorkDay;
import com.example.pethealth.repositories.WorkDayRepository;
import com.example.pethealth.service.parent.IWorkDayService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class WorkDayService implements IWorkDayService {

    private final WorkDayRepository workDayRepository;

    public WorkDayService(WorkDayRepository workDayRepository) {
        this.workDayRepository = workDayRepository;
    }

    @Override
    public BaseDTO createWorkDay(WorkDay workDay) {
        workDayRepository.save(workDay);
        return BaseDTO.builder()
                .message("Tạo thời gian làm việc thành công")
                .result(true)
                .build();
    }

    @Override
    public BaseDTO getAllWorkDay() {
        List<WorkDay> workDayList = workDayRepository.findAll();
        return BaseDTO.builder()
                .result(true).results(workDayList)
                .build();
    }

    @Override
    public BaseDTO updateWorkDay(WorkDay workDayDTO, Long workDayId) {
        WorkDay workDay = workDayRepository.findById(workDayId).orElseThrow(
                () -> new BadRequestException("dont find word day")
        );
        if(workDayDTO.getStartTime() != null){
            workDay.setStartTime(workDayDTO.getStartTime());
        }
        if(workDayDTO.getEndTime() != null){
            workDay.setEndTime(workDayDTO.getEndTime());
        }
        if(workDayDTO.getSessionDay() != null){
            workDay.setSessionDay(workDayDTO.getSessionDay());
        }
        workDayRepository.save(workDay);
        return BaseDTO.builder()
                .message("Cập nhật thành công")
                .result(true)
                .build();
    }
}
