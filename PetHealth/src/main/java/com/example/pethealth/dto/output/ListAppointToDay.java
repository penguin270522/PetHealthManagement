package com.example.pethealth.dto.output;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListAppointToDay {
    private String message;
    private boolean results;
    private int totalAppointToday;
    private List<AppointmentDoctorOutput> appointmentDoctorOutputs = new ArrayList<>();
}
