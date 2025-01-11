package com.example.pethealth.dto.appointmentDTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorAppointment {
    private String fullNameDoctor;
    private Long id;
}
