package com.example.pethealth.dto.outputDTO;

import com.example.pethealth.enums.GenderPet;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicalReportDTO {
    private String namePet;
    private String oldPet;
    private String weightPet;
    private GenderPet genderPet;
    private String symptom;
    private String  numberPhone;
    private String address;
    private String petOwner;
    private LocalDateTime followSchedule;
    private String diagnosed;
    private Integer updateBy;
    private String createdDate;
    private Date updatedDate;
    private String createBy;
    private Long appointmentId;
}
