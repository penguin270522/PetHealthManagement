package com.example.pethealth.repositories;

import com.example.pethealth.model.ServiceMedical;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceMedicalRepository extends JpaRepository<ServiceMedical, Long> {
    Optional<ServiceMedical> findById(long id);
}
