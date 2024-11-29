package com.example.pethealth.repositories;

import com.example.pethealth.model.TypeMedicine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypeMedicineRepository extends JpaRepository<TypeMedicine, Long> {
    @Override
    Optional<TypeMedicine> findById(Long aLong);
}
