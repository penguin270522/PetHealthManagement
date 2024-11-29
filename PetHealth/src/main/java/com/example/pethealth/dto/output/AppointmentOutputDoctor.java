package com.example.pethealth.dto.output;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentOutputDoctor {
    private long id;
    private String fullNameUser;
    private String namePet;
    private String urlUser;
    private String urlPet;
    private String codeAppointment;
    private String numberPhone;
    private String startDate;
    private String statusAppointment;
    private String message;
    private String typeClient;
    private String replayAppointment;
    private String serviceMedical;
}
