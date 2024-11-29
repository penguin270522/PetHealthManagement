package com.example.pethealth.dto.prescriptionDTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicineOutPut {
    private long id;
    private String name;
    private String instructionsForUse;
    private Long countMedicine;
}
