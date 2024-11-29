package com.example.pethealth.dto.prescriptionDTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrescriptionInput {
    private String namePet;
    private String genderPet;
    private double oldPet;
    private String namePetOwen;
    private String address;
    private String symptom;
    private String diagnosed;
    private String note;
    private List<PrescriptionMedicineInput> medicineInputList;
    private double totalMedicine;
}
