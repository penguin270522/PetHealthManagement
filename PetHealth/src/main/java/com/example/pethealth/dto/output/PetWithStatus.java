package com.example.pethealth.dto.output;

import com.example.pethealth.model.Pet;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetWithStatus {
    private String message;
    private boolean results;
    private List<Pet> petList;
    private int totalAppointmentActive;
    private int totalMedicalActive;

}
