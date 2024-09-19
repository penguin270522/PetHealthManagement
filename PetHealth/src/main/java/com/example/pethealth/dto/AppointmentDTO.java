package com.example.pethealth.dto;

import com.example.pethealth.model.Appointment;
import lombok.*;
import org.springframework.web.bind.annotation.BindParam;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentDTO {
    private String message;
    private boolean result;
    private List<Appointment> appointmentList;
}
