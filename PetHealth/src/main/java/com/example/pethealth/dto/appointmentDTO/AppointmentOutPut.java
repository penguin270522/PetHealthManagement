package com.example.pethealth.dto.appointmentDTO;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentOutPut {
    private String code;
    private String startDate;
    private String namePet;
    private String doctor;
    private String status;
    private String message;
}
