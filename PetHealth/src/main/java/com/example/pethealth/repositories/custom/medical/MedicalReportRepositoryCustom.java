package com.example.pethealth.repositories.custom.medical;

import com.example.pethealth.dto.outputDTO.SimpleResponese;
import com.example.pethealth.model.MedicalReport;
import org.springframework.data.domain.Pageable;

import java.util.Map;
public interface MedicalReportRepositoryCustom {
    SimpleResponese<MedicalReport> findByCondition(Map<String, String> params, Pageable pageable);
}
