package com.example.pethealth.service.parent;

import com.example.pethealth.dto.BaseDTO;
import com.example.pethealth.dto.MedicalReportDTO;
import com.example.pethealth.dto.PageDTO;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface IMedicalReportService {
    BaseDTO createMedicalReport(MedicalReportDTO medicalReportDTO);
    PageDTO getAllMedicalReport(Map<String , String> params);
    PageDTO searchMedicalReport(Map<String , String > params);
}
