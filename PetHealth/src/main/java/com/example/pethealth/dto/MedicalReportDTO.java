package com.example.pethealth.dto;

import com.example.pethealth.enums.GenderPet;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicalReportDTO {
    private String namePet;
    private String oldPet;
    private String weightPet;
    private GenderPet typePet;
    private String symptom;
    private String  numberPhone;
    private String address;
    private String petOwner;
    private LocalDateTime followSchedule;
    private String diagnosed;
}
