package com.example.pethealth.service;

import com.example.pethealth.components.MapperDateUtil;
import com.example.pethealth.dto.authDTO.UserPetWithMedicalReportOutPut;
import com.example.pethealth.dto.invoiceDTO.InvoiceBase;
import com.example.pethealth.dto.invoiceDTO.InvoiceInput;
import com.example.pethealth.dto.invoiceDTO.InvoiceOutPut;
import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.dto.outputDTO.PageDTO;
import com.example.pethealth.enums.InvoiceStatus;
import com.example.pethealth.enums.PaymentMethod;
import com.example.pethealth.exception.BadRequestException;
import com.example.pethealth.model.*;
import com.example.pethealth.repositories.InvoiceRepository;
import com.example.pethealth.repositories.MedicalRepository;
import com.example.pethealth.service.parent.IInvoiceService;
import com.example.pethealth.service.profile.ProfileService;
import com.example.pethealth.utils.ConverDateTime;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InvoiceService implements IInvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final MedicalRepository medicalRepository;
    private final ProfileService profileService;

    private final InvoiceServiceMedicalService invoiceServiceMedicalService;


    public InvoiceService(InvoiceRepository invoiceRepository, MedicalRepository medicalRepository, ProfileService profileService, InvoiceServiceMedicalService invoiceServiceMedicalService) {
        this.invoiceRepository = invoiceRepository;
        this.medicalRepository = medicalRepository;
        this.profileService = profileService;
        this.invoiceServiceMedicalService = invoiceServiceMedicalService;
    }

    @Override
    public BaseDTO createInvoice(InvoiceInput invoiceInput, Long medicalReportId) {
        try {
            MedicalReport medicalReport = medicalRepository.findById(medicalReportId)
                    .orElseThrow(() -> new BadRequestException("Cannot find medical report with id " + medicalReportId));

            User doctor = profileService.getLoggedInUser();

            Invoice invoice = Invoice.builder()
                    .status(InvoiceStatus.PENDING)
                    .discountAmount(invoiceInput.getDiscountAmount())
                    .note(invoiceInput.getNote())
                    .doctor(doctor)
                    .medicalReport(medicalReport)
                    .paymentMethod(invoiceInput.getPaymentMethod())
                    .build();
            invoiceRepository.save(invoice);
            invoiceInput.getServiceMedicalId().forEach(serviceId ->
                    invoiceServiceMedicalService.createInvoiceServiceMedical(serviceId, invoice.getId())
            );

            long totalServicePrice = invoiceServiceMedicalService.findByInvoiceId(invoice.getId()).stream()
                    .mapToLong(service -> (long) service.getServiceMedical().getFeeService())
                    .sum();
            invoice.setTotalServicePrice(totalServicePrice);
            if (medicalReport.getPrescription() != null) {
                invoice.setPrescription(medicalReport.getPrescription());
                invoice.setTotalPrescription((long) medicalReport.getPrescription().getTotalPrice());
                long total = invoice.getTotalPrescription() + invoice.getTotalServicePrice() - invoice.getDiscountAmount();
                invoice.setTotal(total);
                setInvoiceStatus(invoice, invoiceInput.getAmountReceived());
                invoice.setAmountReceived(invoiceInput.getAmountReceived());
                invoiceRepository.save(invoice);
            }
            long total = invoice.getTotalServicePrice() - invoice.getDiscountAmount();
            invoice.setTotal(total);
            setInvoiceStatus(invoice, invoiceInput.getAmountReceived());
            invoice.setAmountReceived(invoiceInput.getAmountReceived());
            invoiceRepository.save(invoice);
            return BaseDTO.builder()
                    .message("Invoice created successfully")
                    .result(true)
                    .build();
        } catch (BadRequestException e) {
            return BaseDTO.builder()
                    .message(e.getMessage())
                    .result(false)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return BaseDTO.builder()
                    .message("An unexpected error occurred: " + e.getMessage())
                    .result(false)
                    .build();
        }
    }

    private void setInvoiceStatus(Invoice invoice, long amountReceived) {
        if (amountReceived >= invoice.getTotal()) {
            invoice.setStatus(InvoiceStatus.PAID);
        } else if (amountReceived > 0) {
            invoice.setStatus(InvoiceStatus.PARTIALLY_PAID);
        } else {
            invoice.setStatus(InvoiceStatus.PENDING);
        }
    }

    @Override
    public BaseDTO updateInvoice(InvoiceInput invoiceInput, Long InvoiceId) {
        return null;
    }

    @Override
    public BaseDTO deleteInvocioe(Long InvoiceId) {
        return null;
    }

    @Override
    public PageDTO getAllInvoice(Map<String, String> params) {
        return null;
    }

    @Override
    public BaseDTO getInvoiceDetailWithMedicalReport(Long medicalReportId) {
        return null;
    }

    @Override
    public UserPetWithMedicalReportOutPut getInformationWithMedicalReportId(Long id) {
        try {
            MedicalReport medicalReport = medicalRepository.findById(id)
                    .orElseThrow(() -> new BadRequestException("Cannot find medical report with id " + id));
            String profilePet = medicalReport.getNamePet() + " - " + medicalReport.getWeightPet() + " kg" + " - " + medicalReport.getGenderPet().getDisplayName();
            String userName = medicalReport.getPrescription() != null ? medicalReport.getPrescription().getOwenPet() : medicalReport.getPetOwner();
            UserPetWithMedicalReportOutPut.UserPetWithMedicalReportOutPutBuilder builder = UserPetWithMedicalReportOutPut.builder()
                    .message("success")
                    .result(true)
                    .userName(userName)
                    .profilePet(profilePet);
            if (medicalReport.getPrescription() != null) {
                builder.totalPrescription(medicalReport.getPrescription().getTotalPrice())
                        .codePrescription(medicalReport.getCode());
            }
            if (medicalReport.getAppointment() != null) {
                builder.serviceMedicalList(medicalReport.getAppointment().getServiceMedical());
            }
            return builder.build();
        } catch (BadRequestException e) {
            return UserPetWithMedicalReportOutPut.builder()
                    .message(e.getMessage())
                    .result(false)
                    .build();
        }
    }

    @Override
    public InvoiceBase getAllInvoiceWithIdUser(Long id) {
        return null;
    }

    @Override
    public InvoiceBase getAllInvoiceWithIdDoctor(Long id) {
        List<Invoice> items = invoiceRepository.findByDoctorId(id);
        List<InvoiceOutPut> results = items.stream().map(
              invoice -> InvoiceOutPut.builder()
                      .code(invoice.getCode())
                      .namePet(invoice.getMedicalReport().getNamePet())
                      .nameDoctor(invoice.getDoctor().getFullName())
                      .fullNameClient(invoice.getMedicalReport().getPetOwner())
                      .totalPrice(invoice.getTotal())
                      .totalAmountPaid(invoice.getAmountReceived())
                      .totalPriceDontPaid(invoice.getTotal() - invoice.getDiscountAmount() - invoice.getAmountReceived())
                      .discountAmount(invoice.getDiscountAmount())
                      .createDate(MapperDateUtil.getDate(invoice.getCreatedDate()))
                      .build()
        ).collect(Collectors.toList());
        return InvoiceBase.builder()
                .message("success")
                .result(true)
                .invoiceOutPut(results)
                .build();
    }
}
