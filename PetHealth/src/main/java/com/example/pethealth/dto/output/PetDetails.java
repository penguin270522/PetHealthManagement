package com.example.pethealth.dto.output;

import com.example.pethealth.dto.appointmentDTO.AppointmentOutPut;
import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PetDetails {
    private String namePet;
    private int oldPet;
    private String genderPet;
    private String colorPet;
    private String birthDay;
    private String adoptive;
    private String crystal;
    private String status;
    private List<AppointmentOutPut> listAppointmentOfPet;
    private List<MedicalReportOutput> listMedicalReport;
    private String urlImagePet;
    private int totalAppointment;
    private int totalMedicalReport;
}
