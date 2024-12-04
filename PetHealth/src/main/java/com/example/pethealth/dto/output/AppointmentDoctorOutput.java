package com.example.pethealth.dto.output;

import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDoctorOutput {
    private String url;
    private String namePet;
    private String dateAppointment;
    private String status;
    private String message;
    private String date;
    private String time;
    private String fullNameUser;
    private String serviceMedical;
}
