package com.example.pethealth.dto.outputDTO;

import com.example.pethealth.enums.AppointmentStatus;
import com.example.pethealth.model.TypePet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentListResponse {
    private String code;

    private String nameUser;

    private String numberPhone;

    private String namePet;


    private TypePet typePet;


    private LocalDateTime startDate;


    private AppointmentStatus appointmentStatus;
}
