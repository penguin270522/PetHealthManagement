package com.example.pethealth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Random;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Prescription  extends BaseEntity{

    private String note;

    private double totalPrice;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    @JsonIgnore
    private User doctor;

    @OneToOne
    @JoinColumn(name = "medical_report_id")
    @JsonIgnore
    private MedicalReport medicalReport;

    @OneToOne(mappedBy = "prescription", cascade = CascadeType.ALL, orphanRemoval = true)
    private Invoice invoice;

    private String namePet;
    private Long oldPet;
    private String genderPet;
    private String owenPet;
    private String address;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String diagnosed;
    private String reasonForExamination;

    @Column(nullable = false, unique = true)
    private String code;

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
