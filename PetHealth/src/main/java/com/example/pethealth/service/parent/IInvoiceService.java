package com.example.pethealth.service.parent;

import com.example.pethealth.dto.authDTO.UserPetWithMedicalReportOutPut;
import com.example.pethealth.dto.invoiceDTO.InvoiceBase;
import com.example.pethealth.dto.invoiceDTO.InvoiceInput;
import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.dto.outputDTO.PageDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Service
public interface IInvoiceService {
    BaseDTO createInvoice(InvoiceInput invoiceInput, Long medicalReportId);
    BaseDTO updateInvoice(InvoiceInput invoiceInput, Long InvoiceId);
    BaseDTO deleteInvocioe( Long InvoiceId);

    PageDTO getAllInvoice(Map<String , String> params);

    BaseDTO getInvoiceDetailWithMedicalReport(Long medicalReportId);

    UserPetWithMedicalReportOutPut getInformationWithMedicalReportId(Long id);

    InvoiceBase getAllInvoiceWithIdUser(Long id);
    InvoiceBase getAllInvoiceWithIdDoctor(Long id);
}
