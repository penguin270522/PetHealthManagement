package com.example.pethealth.service.parent;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.dto.outputDTO.PageDTO;
import com.example.pethealth.dto.outputDTO.PetDTO;
import com.example.pethealth.dto.output.CreatePetOutPut;
import com.example.pethealth.dto.output.PetDetails;
import com.example.pethealth.dto.output.PetWithStatus;

import java.util.Map;

public interface IPetService {
    CreatePetOutPut createPet(PetDTO petDTO);
    PageDTO getAllPet(Map<String , String> params);
    PetDetails findByIdPet(long petId);

    PetWithStatus getPetWithStatusAppointmentACTIVE(Map<String, String> params);

    PetWithStatus getPetWithMedicalReport(Map<String,String> params);

    BaseDTO updatePet(Long petId, PetDTO petDTO);

    BaseDTO deletePet(Long petId);
}
