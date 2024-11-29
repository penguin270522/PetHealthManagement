package com.example.pethealth.service.parent;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.model.InvoiceServiceMedical;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IInvoiceServiceMedicalService {
    BaseDTO createInvoiceServiceMedical(Long serviceMedicalReportId, Long invoiceId);
    BaseDTO deleteInvoiceServiceMedical(Long invoiceServiceMedicalId);
    List<InvoiceServiceMedical> findByInvoiceId(Long invoiceId);
}
