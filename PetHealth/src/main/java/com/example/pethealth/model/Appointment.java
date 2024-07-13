package com.example.pethealth.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Appointment extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false, unique = false)
    private String petOwner;

    @Column(nullable = false, unique = false)
    private String numberPhone;

    @Column(nullable = false, unique = false)
    private String namePet;

    @Column(nullable = false, unique = false)
    private Date appointmentDate;

    @ManyToOne
    @JoinColumn(name="type_id")
    private TypePet typePet;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;


}
