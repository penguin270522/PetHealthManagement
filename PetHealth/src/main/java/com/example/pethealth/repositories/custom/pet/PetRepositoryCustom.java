package com.example.pethealth.repositories.custom.pet;

import com.example.pethealth.dto.outputDTO.SimpleResponese;
import com.example.pethealth.model.Pet;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface PetRepositoryCustom {
    SimpleResponese<Pet> getAllPet(Map<String, String> maps, Pageable pageable);
    SimpleResponese<Pet> getPetWithStatus(Map<String, String> maps,Pageable pageable );
}
