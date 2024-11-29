package com.example.pethealth.service;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.dto.prescriptionDTO.PrescriptionMedicineInput;
import com.example.pethealth.exception.BadRequestException;
import com.example.pethealth.model.Medicine;
import com.example.pethealth.model.Prescription;
import com.example.pethealth.model.PrescriptionMedicine;
import com.example.pethealth.repositories.MedicineRepository;
import com.example.pethealth.repositories.PrescriptionMedicineRepository;
import com.example.pethealth.repositories.PrescriptionRepository;
import com.example.pethealth.service.parent.IPrescriptionMedicineService;
import org.springframework.stereotype.Service;

@Service
public class PrescriptionMedicineService implements IPrescriptionMedicineService {
    private final PrescriptionRepository prescriptionRepository;
    private final PrescriptionMedicineRepository prescriptionMedicineRepository;
    private final MedicineRepository medicineRepository;

    public PrescriptionMedicineService(PrescriptionRepository prescriptionRepository, PrescriptionMedicineRepository prescriptionMedicineRepository, MedicineRepository medicineRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.prescriptionMedicineRepository = prescriptionMedicineRepository;
        this.medicineRepository = medicineRepository;
    }

    @Override
    public BaseDTO createPrescriptionMedicine(PrescriptionMedicineInput prescriptionMedicineInput, Long prescriptionId) {
        try{
            Prescription prescription = prescriptionRepository.findById(prescriptionId)
                    .orElseThrow(
                            ()-> new BadRequestException("dont find by id with id = " + prescriptionId)
                    );
            Medicine medicine = medicineRepository.findById(prescriptionMedicineInput.getMedicineId()).orElseThrow(
                    ()-> new BadRequestException("dont find by id with id = " + prescriptionMedicineInput.getMedicineId())
            );
            if(medicine.getCountMedicine() >= prescriptionMedicineInput.getCountMedicine()){
                PrescriptionMedicine newPrescriptionMedicine = PrescriptionMedicine.builder()
                        .prescription(prescription)
                        .medicine(medicine)
                        .quantity(prescriptionMedicineInput.getCountMedicine())
                        .tutorialMedicine(prescriptionMedicineInput.getInstructionsForUse())
                        .build();
                prescriptionMedicineRepository.save(newPrescriptionMedicine);
            }else{
                return BaseDTO.builder()
                        .message("số lượng thuốc trong kho không đủ")
                        .result(true)
                        .build();
            }
            medicine.setCountMedicine(medicine.getCountMedicine() - prescriptionMedicineInput.getCountMedicine());
            medicineRepository.save(medicine);
            return BaseDTO.builder()
                    .result(true)
                    .message("success")
                    .build();
        }catch (BadRequestException e){
            return BaseDTO.builder()
                    .message(e.getMessage())
                    .result(false)
                    .build();
        }
    }

    @Override
    public BaseDTO deletePrescriptionMedicine(long id) {
        PrescriptionMedicine deletePrescriptionMedicine = prescriptionMedicineRepository.findById(id)
                .orElseThrow(
                        ()-> new BadRequestException("dont find by id with id = " + id)
                );
        prescriptionMedicineRepository.delete(deletePrescriptionMedicine);
        return BaseDTO.builder()
                .result(true)
                .message("success")
                .build();
    }

    @Override
    public BaseDTO updatePrescriptionMedicine(long id, PrescriptionMedicineInput prescriptionMedicine) {
        PrescriptionMedicine updatePrescriptionMedicine = prescriptionMedicineRepository.findById(id)
                .orElseThrow(
                        ()-> new BadRequestException("dont find by id with id = " + id)
                );
        if(!prescriptionMedicine.getInstructionsForUse().isEmpty()){
            updatePrescriptionMedicine.setTutorialMedicine(prescriptionMedicine.getInstructionsForUse());
        }
        if(prescriptionMedicine.getCountMedicine() != null){
            updatePrescriptionMedicine.setQuantity(prescriptionMedicine.getCountMedicine());
        }
        prescriptionMedicineRepository.save(updatePrescriptionMedicine);
        return BaseDTO.builder()
                .result(true)
                .message("success")
                .build();
    }
}
