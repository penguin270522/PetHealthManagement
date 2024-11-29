package com.example.pethealth.service.parent;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.dto.prescriptionDTO.PrescriptionDetailOutput;
import com.example.pethealth.dto.prescriptionDTO.PrescriptionInput;
import org.springframework.stereotype.Service;

@Service
public interface IPresciptionService {
    BaseDTO createPrestion(PrescriptionInput prescriptionInput, long medicalReportId);
    BaseDTO updatePrescion(long id, PrescriptionInput prescriptionInput);

    BaseDTO getAllPrescription();

    PrescriptionDetailOutput getPrescriptionDetailWithMedicalReport(long id);
}
