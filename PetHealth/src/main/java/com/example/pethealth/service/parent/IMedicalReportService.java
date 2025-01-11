package com.example.pethealth.service.parent;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.dto.outputDTO.MedicalReportDTO;
import com.example.pethealth.dto.outputDTO.PageDTO;
import com.example.pethealth.dto.outputDTO.SimpleResponese;
import com.example.pethealth.dto.output.MedicalReportOutput;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface IMedicalReportService {
    BaseDTO createMedicalReport(MedicalReportDTO medicalReportDTO);
    SimpleResponese<MedicalReportOutput> getAllMedicalReport(Map<String , String> params);
    PageDTO searchMedicalReport(Map<String , String > params);

    BaseDTO deleteMedicalReport(Long medicalReportId);
}
