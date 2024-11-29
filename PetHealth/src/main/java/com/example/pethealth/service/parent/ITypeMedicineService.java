package com.example.pethealth.service.parent;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.model.TypeMedicine;
import org.springframework.stereotype.Service;

@Service
public interface ITypeMedicineService {
    BaseDTO createTypeMedicine(TypeMedicine typeMedicine);
    BaseDTO getAllMedicine();
    BaseDTO deleteTypeMedicine(long typeMedicineId);
    BaseDTO updateTypeMedicine(long typeMedicineId);
}
