package com.example.pethealth.model;

import com.example.pethealth.enums.AppointmentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Appointment extends BaseEntity {

   @Column(nullable = false, unique = true) //nullable: false -> khong dc null
   private String code;

   @Column(nullable = false)
   private String nameUser;

   private String replayAppointment;

   @Column(nullable = false)
   private String numberPhone;

   @Column(nullable = false)
   private String namePet;

   @ManyToOne
   @JoinColumn(name="type_pet_id")
   private TypePet typePet;

   @Column(nullable = false)
   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
   private LocalDateTime startDate;

   @Enumerated(EnumType.STRING)
   private AppointmentStatus appointmentStatus;

   @ManyToOne
   @JoinColumn(name = "doctor_id")
   @JsonIgnore
   private User doctorInCharge;

   @ManyToOne
   @JoinColumn(name = "pet_id")
   @JsonIgnore
   private Pet pet;

   @OneToOne(mappedBy = "appointment")
   private MedicalReport medicalReport;

   private String message;

   @ManyToOne
   @JoinColumn(name = "service_id")
   @JsonIgnore
   private ServiceMedical serviceMedical;

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
