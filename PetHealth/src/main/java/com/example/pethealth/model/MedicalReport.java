package com.example.pethealth.model;

import com.example.pethealth.enums.GenderPet;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class MedicalReport extends BaseEntity {

    @Column(nullable = false, unique = true)
    //nullable = false -> not null
    // unique = false -> only attribute
    private String code;

    @Column(nullable = true, unique = false)
    private String petOwner; // luu tru ten nguoi nuoi pet

    @Column(nullable = true, unique = false)
    private String namePet;

    @Enumerated(EnumType.STRING)
    private GenderPet genderPet;

    @Column(nullable = false,unique = false)
    private String numberPhone;

    @Column(nullable = false, unique = false)
    private String address;

    @Column(nullable = false, unique = false)
    private String symptom;

    @Column(nullable = false, unique = false)
    private String diagnosed;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;
}
