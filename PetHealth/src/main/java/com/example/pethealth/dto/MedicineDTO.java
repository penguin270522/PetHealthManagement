package com.example.pethealth.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicineDTO {
    private String name;
    private String information;
    private Long price;
    private Long countMedicine;
    private String unitMedicine;
}
