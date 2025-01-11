package com.example.pethealth.dto.output;

import com.example.pethealth.enums.GenderPet;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicalReportOutput {
    private long id;
    private String code;
    private String namePet;
    private String oldPet;
    private String weightPet;
    private GenderPet genderPet;
    private String symptom;
    private String  numberPhone;
    private String address;
    private String petOwner;
    private String followSchedule;
    private String diagnosed;
    private Integer updateBy;
    private String createdDate;
    private Date updatedDate;
    private String createBy;
    private String urlPet;
    private String nameDoctor;
    private boolean havePrescription = true;
    private boolean haveInvoice = true;
}
