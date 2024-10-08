package com.example.pethealth.service;

import com.example.pethealth.config.ModelMapperConfig;
import com.example.pethealth.dto.BaseDTO;
import com.example.pethealth.dto.MedicalReportDTO;
import com.example.pethealth.dto.PageDTO;
import com.example.pethealth.model.MedicalReport;
import com.example.pethealth.repositories.MedicalRepository;
import com.example.pethealth.service.parent.IMedicalReportService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MedicalReportService implements IMedicalReportService {
    private ModelMapper modelMapper;
    private static MedicalRepository medicalRepository;
    @Override
    public BaseDTO createMedicalReport(MedicalReportDTO medicalReportDTO) {
        try{
            MedicalReport medicalReport = modelMapper.map(medicalReportDTO, MedicalReport.class);
            medicalRepository.save(medicalReport);
            return BaseDTO.builder()
                    .message("success")
                    .result(true)
                    .build();
        }catch (Exception e){
            return BaseDTO.builder()
                    .message(e.getMessage())
                    .result(false)
                    .build();
        }
    }

    @Override
    public PageDTO getAllMedicalReport(Map<String, String> params) {
        return null;
    }

    @Override
    public PageDTO searchMedicalReport(Map<String, String> params) {
        return null;
    }
}
