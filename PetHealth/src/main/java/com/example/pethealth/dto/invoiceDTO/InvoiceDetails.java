package com.example.pethealth.dto.invoiceDTO;

import com.example.pethealth.model.InvoiceMedicine;
import com.example.pethealth.model.InvoiceServiceMedical;
import com.example.pethealth.model.PrescriptionMedicine;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceDetails {
    private String fullName;
    private String numberPhone;
    private String  address;
    private String namePet;
    private String note;
    private List<InvoiceMedicineListDTO> prescriptionMedicineList;
    private List<InvoiceServiceMedical> invoiceServiceMedicalList;
    private Long totalPrescriptionMedical;
    private Long totalInvoiceServiceMedical;
    private Long total;
    private Long amountReceived;
    private String codeInvoice;
    private String fullNameDoctor;
}
