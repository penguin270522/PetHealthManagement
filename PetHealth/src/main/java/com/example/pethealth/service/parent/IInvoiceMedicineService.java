package com.example.pethealth.service.parent;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.dto.prescriptionDTO.PrescriptionMedicineInput;
import org.springframework.stereotype.Service;

@Service
public interface IInvoiceMedicineService {
    BaseDTO createInvoiceMedicine(PrescriptionMedicineInput prescriptionMedicineInput, Long InvoiceId);
}
