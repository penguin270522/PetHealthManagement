package com.example.pethealth.repositories;

import com.example.pethealth.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
    Optional<Invoice> findByPrescriptionId(Long prescriptionId);
    Optional<Invoice> findByMedicalReportId(Long medicalReportId);

    List<Invoice> findByDoctorId(Long id);
}
