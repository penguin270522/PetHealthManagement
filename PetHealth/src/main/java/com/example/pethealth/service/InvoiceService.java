package com.example.pethealth.service;

import com.example.pethealth.components.MapperDateUtil;
import com.example.pethealth.dto.authDTO.UserPetWithMedicalReportOutPut;
import com.example.pethealth.dto.invoiceDTO.*;
import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.dto.outputDTO.PageDTO;
import com.example.pethealth.enums.InvoiceStatus;
import com.example.pethealth.enums.PaymentMethod;
import com.example.pethealth.exception.BadRequestException;
import com.example.pethealth.model.*;
import com.example.pethealth.repositories.InvoiceMedicineRepository;
import com.example.pethealth.repositories.InvoiceRepository;
import com.example.pethealth.repositories.MedicalRepository;
import com.example.pethealth.service.parent.IInvoiceService;
import com.example.pethealth.service.profile.ProfileService;
import com.example.pethealth.utils.ConverDateTime;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InvoiceService implements IInvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final MedicalRepository medicalRepository;
    private final ProfileService profileService;
    private final InvoiceMedicineService invoiceMedicineService;
    private final InvoiceMedicineRepository invoiceMedicineRepository;
    private final InvoiceServiceMedicalService invoiceServiceMedicalService;


    public InvoiceService(InvoiceRepository invoiceRepository, MedicalRepository medicalRepository, ProfileService profileService, InvoiceMedicineService invoiceMedicineService, InvoiceMedicineRepository invoiceMedicineRepository, InvoiceServiceMedicalService invoiceServiceMedicalService) {
        this.invoiceRepository = invoiceRepository;
        this.medicalRepository = medicalRepository;
        this.profileService = profileService;
        this.invoiceMedicineService = invoiceMedicineService;
        this.invoiceMedicineRepository = invoiceMedicineRepository;
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
            if(medicalReport.getInvoice() != null){
                return BaseDTO.builder()
                        .message("Phiếu khám đã có hóa đơn")
                        .result(false)
                        .build();
            }
            invoiceRepository.save(invoice);
            invoiceInput.getServiceMedicalId().forEach(serviceId ->
                    invoiceServiceMedicalService.createInvoiceServiceMedical(serviceId, invoice.getId())
            );
            invoiceInput.getMedicineInputList().forEach(prescriptionMedicineInput ->
                    invoiceMedicineService.createInvoiceMedicine(prescriptionMedicineInput, invoice.getId())
                    );
            long totalServicePrice = invoiceServiceMedicalService.findByInvoiceId(invoice.getId()).stream()
                    .mapToLong(service -> (long) service.getServiceMedical().getFeeService())
                    .sum();
            invoice.setTotalServicePrice(totalServicePrice);
            long totalMedicinePrice = invoiceMedicineRepository.findByInvoiceId(invoice.getId()).stream()
                    .mapToLong(invoiceMedicine -> (long) invoiceMedicine.getMedicine().getPrice() * invoiceMedicine.getQuantity()).sum();
            long total = invoice.getTotalServicePrice() + totalMedicinePrice - invoice.getDiscountAmount();
            invoice.setTotal(total);
            invoice.setTotalPrescription(totalMedicinePrice);
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
                      .id(invoice.getId())
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

    @Override
    public BaseDTO invoiceDetails(long invoiceDetailsId) {
        Invoice invoice = invoiceRepository.findById(invoiceDetailsId).orElseThrow(
                ()-> new BadRequestException("dont find by id = " + invoiceDetailsId)
        );

        List<InvoiceMedicineListDTO> results = new ArrayList<>();
        for(InvoiceMedicine item: invoice.getInvoiceMedicines()){
            InvoiceMedicineListDTO newItem = new InvoiceMedicineListDTO();
            newItem.setName(item.getMedicine().getName());
            newItem.setFee(item.getMedicine().getPrice());
            newItem.setQuality(item.getQuantity());
            newItem.setTotalFee(item.getQuantity() * item.getMedicine().getPrice());
            results.add(newItem);
        }
        InvoiceDetails invoiceDetails = InvoiceDetails.builder()
                .codeInvoice(invoice.getCode())
                .fullName(invoice.getMedicalReport().getPetOwner())
                .address(invoice.getMedicalReport().getAddress())
                .fullNameDoctor(invoice.getDoctor().getFullName())
                .numberPhone(invoice.getMedicalReport().getNumberPhone())
                .namePet(invoice.getMedicalReport().getNamePet())
                .note(invoice.getNote())
                .invoiceServiceMedicalList(invoice.getInvoiceServiceMedical())
                .prescriptionMedicineList(results)
                .totalPrescriptionMedical(invoice.getTotalPrescription())
                .totalInvoiceServiceMedical(invoice.getTotalServicePrice())
                .total(invoice.getTotal())
                .amountReceived(invoice.getAmountReceived())
                .codeInvoice(invoice.getCode())
                .build();
        return BaseDTO.builder()
                .message("success")
                .result(true)
                .object(invoiceDetails)
                .build();
    }
}
