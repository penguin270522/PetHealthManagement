package com.example.pethealth.model;

import com.example.pethealth.enums.AppointmentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
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
