package com.example.pethealth.service.parent;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.model.Medicine;
import org.springframework.stereotype.Service;

@Service
public interface IMedicineService {
    BaseDTO createMedicine(Medicine medicine, long id);
    BaseDTO getAllMedicine();

    BaseDTO getAllMedicineWithTypeMedicine(long id);
    BaseDTO deleteMedicine(long medicineId);
    BaseDTO updateMedicine(long medicineId, Medicine medicine);
}
