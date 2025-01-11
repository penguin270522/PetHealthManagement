package com.example.pethealth.repositories;

import com.example.pethealth.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
    Optional<Invoice> findByPrescriptionId(Long prescriptionId);
    Optional<Invoice> findByMedicalReportId(Long medicalReportId);
    List<Invoice> findByDoctorId(Long id);

    @Query("SELECT SUM(i.amountReceived) FROM Invoice i WHERE i.doctor.id = :doctorId")
    Long calculateTotalAmountReceivedByDoctorId(@Param("doctorId") Long doctorId);

    @Query("SELECT SUM(i.amountReceived) FROM Invoice i WHERE YEAR(i.createdDate) = :year AND MONTH(i.createdDate) = :month")
    Long getTotalAmountReceivedOfYearAndMonth(@Param("year") int year, @Param("month") int month);

    @Query("SELECT i FROM Invoice i where YEAR(i.createdDate) = :year AND MONTH(i.createdDate) = :month")
    List<Invoice> getInvoiceWithYearAndMonth(@Param("year") int year, @Param("month") int month);
    @Query("SELECT SUM(i.amountReceived) FROM Invoice i WHERE YEAR(i.createdDate) = :year")
    Long getTotalAmountReceivedOfYear(@Param("year") int year);

    @Query("SELECT count(i) from Invoice i")
    Long getCountInvoice();

}
