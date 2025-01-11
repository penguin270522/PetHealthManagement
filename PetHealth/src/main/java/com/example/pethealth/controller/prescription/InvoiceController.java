package com.example.pethealth.controller.prescription;

import com.example.pethealth.dto.authDTO.UserPetWithMedicalReportOutPut;
import com.example.pethealth.dto.invoiceDTO.InvoiceBase;
import com.example.pethealth.dto.invoiceDTO.InvoiceInput;
import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.service.InvoiceService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {
    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping("/createInvoice/{id}")
    public BaseDTO createInvoice(@PathVariable Long id, @RequestBody InvoiceInput invoiceInput){
        return invoiceService.createInvoice(invoiceInput,id);
    }

    @GetMapping("/getUserAndPetWithMedicalReport/{id}")
    public UserPetWithMedicalReportOutPut getInformationWithMedicalReportId(@PathVariable Long id){
        return invoiceService.getInformationWithMedicalReportId(id);
    }

    @GetMapping("/getAllInvoiceWithId/{id}")
    public InvoiceBase getAllInvocieWithId(@PathVariable Long id){
        return invoiceService.getAllInvoiceWithIdDoctor(id);
    }

    @GetMapping("/invoiceDetail/{id}")
    public BaseDTO invoiceDetail(@PathVariable long id){
        return invoiceService.invoiceDetails(id);
    }
}
