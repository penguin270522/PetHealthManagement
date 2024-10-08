package com.example.pethealth.repositories;

import com.example.pethealth.model.MedicalReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalRepository extends JpaRepository<MedicalReport,Long> {
}
