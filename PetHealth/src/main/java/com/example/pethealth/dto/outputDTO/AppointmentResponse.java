package com.example.pethealth.dto.outputDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponse {
    private String username;
    private String numberPhone;
    private String namePet;
    private LocalDateTime dateTime;
    private String message;
    private long doctorId;
    private long petId;
    private long serviceId;
}
