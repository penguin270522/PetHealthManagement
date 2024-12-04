package com.example.pethealth.dto.invoiceDTO;

import com.example.pethealth.enums.PaymentMethod;
import com.example.pethealth.model.ServiceMedical;
import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceInput {
    private List<Long> serviceMedicalId;
    private Long prescriptionId;
    private Long discountAmount;
    private String note;
    private Long amountReceived;
    private PaymentMethod paymentMethod;
}
