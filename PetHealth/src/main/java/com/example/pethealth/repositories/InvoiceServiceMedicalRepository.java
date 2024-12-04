package com.example.pethealth.repositories;

import com.example.pethealth.model.InvoiceServiceMedical;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceServiceMedicalRepository extends JpaRepository<InvoiceServiceMedical, Long> {
    List<InvoiceServiceMedical> findByInvoiceId(Long id);
}
