package com.example.pethealth.repositories;

import com.example.pethealth.model.TypePet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypePetRepository extends JpaRepository<TypePet, Long> {
    Optional<TypePet> findById(Long id);
}
