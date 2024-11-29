package com.example.pethealth.model;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceServiceMedical extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "service_medical_id")
    private ServiceMedical serviceMedical;
}
