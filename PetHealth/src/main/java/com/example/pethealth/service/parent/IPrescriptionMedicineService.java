package com.example.pethealth.service.parent;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.dto.prescriptionDTO.PrescriptionMedicineInput;
import com.example.pethealth.model.PrescriptionMedicine;
import org.springframework.stereotype.Service;

@Service
public interface IPrescriptionMedicineService {
    BaseDTO createPrescriptionMedicine(PrescriptionMedicineInput medicineInput, Long prescriptionId);
    BaseDTO deletePrescriptionMedicine(long id);
    BaseDTO updatePrescriptionMedicine(long id, PrescriptionMedicineInput prescriptionMedicine);

}
