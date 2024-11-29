package com.example.pethealth.model;

import com.example.pethealth.enums.GenderPet;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String diagnosed;
    @OneToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @Column(nullable = true)
    private LocalDateTime followSchedule;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    @JsonIgnore
    private Pet pet;

    @OneToOne(mappedBy = "medicalReport")
    private Prescription prescription;
    @PrePersist
    public void generateRandomCode() {
        String letters = getRandomLetters(3);
        String numbers = getRandomNumbers(4);
        this.code = letters + numbers;
    }

    public static String getRandomLetters(int length) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder result = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(alphabet.length());
            result.append(alphabet.charAt(index));
        }
        return result.toString();
    }

    public static String getRandomNumbers(int length) {
        String digits = "0123456789";
        Random random = new Random();
        StringBuilder result = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(digits.length());
            result.append(digits.charAt(index));
        }
        return result.toString();
    }

}
