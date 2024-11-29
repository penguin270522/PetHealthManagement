package com.example.pethealth.service;

import com.example.pethealth.dto.MedicineDTO;
import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.exception.BadRequestException;
import com.example.pethealth.model.Medicine;
import com.example.pethealth.model.TypeMedicine;
import com.example.pethealth.repositories.MedicineRepository;
import com.example.pethealth.repositories.TypeMedicineRepository;
import com.example.pethealth.service.parent.IMedicineService;
import com.github.javafaker.Medical;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicineService implements IMedicineService {
    private final MedicineRepository medicineRepository;
    private final TypeMedicineRepository typeMedicineRepository;

    public MedicineService(MedicineRepository medicineRepository, TypeMedicineRepository typeMedicineRepository) {
        this.medicineRepository = medicineRepository;
        this.typeMedicineRepository = typeMedicineRepository;
    }

    @Override
    public BaseDTO createMedicine(Medicine medicine, long id) {
       try{
           TypeMedicine typeMedicine = typeMedicineRepository.findById(id)
                   .orElseThrow(
                           ()-> new BadRequestException("dont find by id with id = " + id)
                   );
           Medicine medicineNew = Medicine.builder()
                   .name(medicine.getName())
                   .countMedicine(medicine.getCountMedicine())
                   .price(medicine.getPrice())
                   .information(medicine.getInformation())
                   .typeMedicine(typeMedicine)
                   .unitMedicine(medicine.getUnitMedicine())
                   .build();
           medicineRepository.save(medicineNew);
           return BaseDTO.builder()
                   .message("success").result(true)
                   .build();
       }catch (BadRequestException e){
           return BaseDTO.builder()
                   .message(e.getMessage())
                   .result(false)
                   .build();
       }
    }

    @Override
    public BaseDTO getAllMedicine() {
        List<Medicine> results = medicineRepository.findAll();
        return BaseDTO.builder()
                .message("success")
                .result(true)
                .results(results)
                .build();
    }

    @Override
    public BaseDTO getAllMedicineWithTypeMedicine(long id) {
        List<Medicine> results = medicineRepository.findByTypeMedicineId(id);
        List<MedicineDTO> medicineDTOS = results.stream()
                .map(items -> MedicineDTO.builder()
                        .name(items.getName())
                        .unitMedicine(
                                items.getUnitMedicine() == null ? "Không xác định" :
                                "VIEN".equals(items.getUnitMedicine().name()) ? "viên" :
                                        "CHAI".equals(items.getUnitMedicine().name()) ? "chai" :
                                                String.valueOf(items.getUnitMedicine())
                        )
                        .price(items.getPrice())
                        .countMedicine(items.getCountMedicine())
                        .information(items.getInformation())
                        .build()).collect(Collectors.toList());

        return BaseDTO.builder()
                .message("success")
                .result(true)
                .results(results)
                .build();
    }


    @Override
    public BaseDTO deleteMedicine(long medicineId) {
        Medicine medicine = medicineRepository.findById(medicineId)
                .orElseThrow(
                        ()-> new BadRequestException("dont find by id with id" + medicineId)
                );
        medicineRepository.delete(medicine);
        return BaseDTO.builder()
                .message("success").result(true)
                .build();
    }

    @Override
    public BaseDTO updateMedicine(long medicineId, Medicine medicine) {
        try{
            Medicine medicineUpdate = medicineRepository.findById(medicineId)
                    .orElseThrow(
                            ()-> new BadRequestException("dont find by id with id" + medicineId)
                    );
            if(medicine.getName() != null && !medicine.getName().isEmpty()){
                medicineUpdate.setName(medicine.getName());
            }
            if(medicine.getCountMedicine() != null && medicine.getCountMedicine() >= 0){
                medicineUpdate.setCountMedicine(medicine.getCountMedicine());
            }
            if(medicine.getInformation() != null && !medicine.getInformation().isEmpty()){
                medicineUpdate.setInformation(medicine.getInformation());
            }

            medicineRepository.save(medicineUpdate);
            return BaseDTO.builder()
                    .message("success").result(true)
                    .build();
        }catch (BadRequestException e){
            return BaseDTO.builder()
                    .message(e.getMessage())
                    .result(false)
                    .build();
        }
    }
}

