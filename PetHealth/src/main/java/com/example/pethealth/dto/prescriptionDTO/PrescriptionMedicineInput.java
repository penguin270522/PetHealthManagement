package com.example.pethealth.dto.prescriptionDTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrescriptionMedicineInput {
    private Long medicineId;
    private Long countMedicine;
    private String instructionsForUse;
}
