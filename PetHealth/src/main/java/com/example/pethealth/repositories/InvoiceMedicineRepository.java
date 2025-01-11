package com.example.pethealth.repositories;

import com.example.pethealth.model.InvoiceMedicine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceMedicineRepository extends JpaRepository<InvoiceMedicine,Long> {

    List<InvoiceMedicine> findByInvoiceId(Long invoiceId);
}
