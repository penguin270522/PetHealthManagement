package com.example.pethealth.repositories;

import com.example.pethealth.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Appointment, Long> {
}
