package com.example.pethealth.model;

import com.example.pethealth.enums.GenderPet;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class MedicalReport extends BaseEntity {

    @Column(nullable = false, unique = true)

    private String code;

    @Column(nullable = true, unique = false)
    private String petOwner;

    @Column(nullable = true, unique = false)
    private String namePet;

    @Column(unique = false, nullable = true)
    private String oldPet;

    private String weightPet;


    @Enumerated(EnumType.STRING)
    private GenderPet genderPet;

    @Column(nullable = false,unique = false)
    private String numberPhone;

    @Column(nullable = false, unique = false)
    private String address;

    @Column(nullable = false, unique = false)
    //symptom for pet
    private String symptom;

    @Column(nullable = false, unique = false)
    //diagnosed -> doctor writing for pet
    private String diagnosed;


    @Column(nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy HH:mm:ss")
    private LocalDateTime followSchedule;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;



}
