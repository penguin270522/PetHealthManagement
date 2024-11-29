package com.example.pethealth.model;

import com.example.pethealth.enums.InvoiceStatus;
import com.example.pethealth.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Random;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Invoice extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="doctor_id")
    private User doctor;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;

    @OneToOne
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;

    private Long amountReceived;

    private Long totalServicePrice;

    private Long totalPrescription;

    private Long discountAmount;

    private Long total;

    private String note;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @OneToOne
    @JoinColumn(name = "medical_report_id")
    private MedicalReport medicalReport;

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
