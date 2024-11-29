package com.example.pethealth.service;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.exception.BadRequestException;
import com.example.pethealth.model.Medicine;
import com.example.pethealth.model.TypeMedicine;
import com.example.pethealth.repositories.TypeMedicineRepository;
import com.example.pethealth.service.parent.ITypeMedicineService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeMedicineService implements ITypeMedicineService {
    private final TypeMedicineRepository typeMedicineRepository;

    public TypeMedicineService(TypeMedicineRepository typeMedicineRepository) {
        this.typeMedicineRepository = typeMedicineRepository;
    }

    @Override
    public BaseDTO createTypeMedicine(TypeMedicine typeMedicine) {
        try {
            TypeMedicine typeMedicineNew = TypeMedicine.builder()
                    .name(typeMedicine.getName())
                    .build();
            typeMedicineRepository.save(typeMedicineNew);
            return BaseDTO.builder()
                    .message("success").result(true)
                    .build();
        }catch (Exception e){
            return BaseDTO.builder()
                    .message(e.getMessage())
                    .result(false)
                    .build();
        }
    }

    @Override
    public BaseDTO getAllMedicine() {
        List<TypeMedicine> results = typeMedicineRepository.findAll();
        return BaseDTO.builder()
                .message("success")
                .result(true)
                .results(results)
                .build();
    }

    @Override
    public BaseDTO deleteTypeMedicine(long typeMedicineId) {
        TypeMedicine typeMedicine = typeMedicineRepository.findById(typeMedicineId)
                .orElseThrow(
                        ()-> new BadRequestException("dont find by id with = " + typeMedicineId)
                );
        typeMedicineRepository.delete(typeMedicine);
        return BaseDTO.builder()
                .message("success")
                .result(true)
                .build();
    }

    @Override
    public BaseDTO updateTypeMedicine(long typeMedicineId) {
        return null;
    }
}
