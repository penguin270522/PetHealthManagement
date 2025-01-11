package com.example.pethealth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceMedicine extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "invoice_id")
    @JsonIgnore
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "medicine_id")
    @JsonIgnore
    private Medicine medicine;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String tutorialMedicine;

    private Long quantity;
}
