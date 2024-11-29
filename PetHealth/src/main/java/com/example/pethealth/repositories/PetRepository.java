package com.example.pethealth.repositories;

import com.example.pethealth.model.Pet;
import com.example.pethealth.repositories.custom.pet.PetRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet,Long>, PetRepositoryCustom {
    Optional<Pet> findById(long id);
}
