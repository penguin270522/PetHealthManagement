package com.example.pethealth.service;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.exception.BadRequestException;
import com.example.pethealth.model.Invoice;
import com.example.pethealth.model.InvoiceServiceMedical;
import com.example.pethealth.model.ServiceMedical;
import com.example.pethealth.repositories.InvoiceRepository;
import com.example.pethealth.repositories.InvoiceServiceMedicalRepository;
import com.example.pethealth.repositories.MedicalRepository;
import com.example.pethealth.repositories.ServiceMedicalRepository;
import com.example.pethealth.service.parent.IInvoiceServiceMedicalService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceServiceMedicalService implements IInvoiceServiceMedicalService {
    private final ServiceMedicalRepository serviceMedicalRepository;
    private final InvoiceRepository invoiceRepository;
    private final InvoiceServiceMedicalRepository invoiceServiceMedicalRepository;

    public InvoiceServiceMedicalService(ServiceMedicalRepository serviceMedicalRepository, InvoiceRepository invoiceRepository, InvoiceServiceMedicalRepository invoiceServiceMedicalRepository) {
        this.serviceMedicalRepository = serviceMedicalRepository;
        this.invoiceRepository = invoiceRepository;
        this.invoiceServiceMedicalRepository = invoiceServiceMedicalRepository;
    }


    @Override
    public BaseDTO createInvoiceServiceMedical(Long serviceMedicalReportId, Long invoiceId) {
        //serviceMedical
        try{
            ServiceMedical serviceMedical = serviceMedicalRepository.findById(serviceMedicalReportId)
                    .orElseThrow(
                            ()->new BadRequestException("dont find serviceMedical with id = " + serviceMedicalReportId)
                    );
            Invoice invoice = invoiceRepository.findById(invoiceId)
                    .orElseThrow(
                            ()->new BadRequestException("dont find invoice with id = " + invoiceId)
                    );
            InvoiceServiceMedical invoiceServiceMedical = InvoiceServiceMedical.builder()
                    .serviceMedical(serviceMedical)
                    .invoice(invoice)
                    .build();
            invoiceServiceMedicalRepository.save(invoiceServiceMedical);
            return BaseDTO.builder()
                    .message("success")
                    .result(true)
                    .build();
        } catch (BadRequestException e){
            return BaseDTO.builder()
                    .message("false")
                    .result(false)
                    .build();
        }
    }

    @Override
    public BaseDTO deleteInvoiceServiceMedical(Long invoiceServiceMedicalId) {
       try{
           InvoiceServiceMedical invoiceServiceMedical = invoiceServiceMedicalRepository.findById(invoiceServiceMedicalId)
                   .orElseThrow(
                           ()->new BadRequestException("dont find invoice with id = " + invoiceServiceMedicalId)
                   );
           invoiceServiceMedicalRepository.delete(invoiceServiceMedical);
           return BaseDTO.builder()
                   .message("success")
                   .result(true)
                   .build();
       }catch (BadRequestException e){
           return BaseDTO.builder()
                   .message("false")
                   .result(false)
                   .build();
       }
    }

    @Override
    public List<InvoiceServiceMedical> findByInvoiceId(Long invoiceId) {
        return invoiceServiceMedicalRepository.findByInvoiceId(invoiceId);
    }
}
