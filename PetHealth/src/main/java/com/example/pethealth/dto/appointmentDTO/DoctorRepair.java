package com.example.pethealth.dto.appointmentDTO;

import com.example.pethealth.enums.AppointmentStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorRepair {
    private AppointmentStatus appointmentStatus;
    private String replayAppointment;
    private Long doctorId;
}
