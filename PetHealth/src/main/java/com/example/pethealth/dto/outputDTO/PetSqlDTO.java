package com.example.pethealth.dto.outputDTO;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetSqlDTO {
    private Long petId;
    private String petName;
    private String petGenderPet;
    private String petTypePet;
    private Long petWeight;
    private Date petBirthDay;
    private String petColor;
    private String petSize;
    private Date petAdoptive;
    private String petCrystal;
    private String petStatusPet;
    private Long appointmentId;
    private String appointmentStatus;
}
