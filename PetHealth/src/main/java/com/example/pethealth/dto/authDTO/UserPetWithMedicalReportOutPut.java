package com.example.pethealth.dto.authDTO;

import com.example.pethealth.model.ServiceMedical;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPetWithMedicalReportOutPut {
    private String message;
    private boolean result;
    private String userName;
    private String profilePet;
    private String codePrescription;
    private double totalPrescription;
    private ServiceMedical serviceMedicalList;
}
