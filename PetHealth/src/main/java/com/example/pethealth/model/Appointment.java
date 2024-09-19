package com.example.pethealth.model;

import com.example.pethealth.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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
   private LocalDateTime startDate;

   @Enumerated(EnumType.STRING)
   private AppointmentStatus appointmentStatus;

   @PrePersist
   public void generateRandomCode() {
      this.code = UUID.randomUUID().toString();
   }
}
