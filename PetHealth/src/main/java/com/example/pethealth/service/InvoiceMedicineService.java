package com.example.pethealth.service;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.dto.prescriptionDTO.PrescriptionMedicineInput;
import com.example.pethealth.exception.BadRequestException;
import com.example.pethealth.model.Invoice;
import com.example.pethealth.model.InvoiceMedicine;
import com.example.pethealth.model.Medicine;
import com.example.pethealth.repositories.InvoiceMedicineRepository;
import com.example.pethealth.repositories.InvoiceRepository;
import com.example.pethealth.repositories.MedicineRepository;
import com.example.pethealth.service.parent.IInvoiceMedicineService;
import org.springframework.stereotype.Service;

@Service
public class InvoiceMedicineService implements IInvoiceMedicineService {
    private final InvoiceRepository invoiceRepository;
    private final InvoiceMedicineRepository invoiceMedicineRepository;
    private final MedicineRepository medicineRepository;

    public InvoiceMedicineService(InvoiceRepository invoiceRepository, InvoiceMedicineRepository invoiceMedicineRepository, MedicineRepository medicineRepository) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMedicineRepository = invoiceMedicineRepository;
        this.medicineRepository = medicineRepository;
    }

    @Override
    public BaseDTO createInvoiceMedicine(PrescriptionMedicineInput prescriptionMedicineInput, Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId).orElseThrow(
                ()-> new BadRequestException("dont find invoice with id = " + invoiceId)
        );
        Medicine medicine = medicineRepository.findById(prescriptionMedicineInput.getMedicineId()).orElseThrow(
                ()-> new BadRequestException("dont find medicine with id = "+ prescriptionMedicineInput.getMedicineId())
        );
        InvoiceMedicine invoiceMedicine = InvoiceMedicine.builder()
                .medicine(medicine)
                .invoice(invoice)
                .quantity(prescriptionMedicineInput.getCountMedicine())
                .build();
        invoiceMedicineRepository.save(invoiceMedicine);
        medicine.setCountMedicine(medicine.getCountMedicine() - prescriptionMedicineInput.getCountMedicine());
        medicineRepository.save(medicine);
        return BaseDTO.builder()
                .result(true).message("success")
                .build();
    }
}
