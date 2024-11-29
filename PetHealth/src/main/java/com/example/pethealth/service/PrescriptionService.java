package com.example.pethealth.service;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.dto.prescriptionDTO.MedicineOutPut;
import com.example.pethealth.dto.prescriptionDTO.PrescriptionDetailOutput;
import com.example.pethealth.dto.prescriptionDTO.PrescriptionInput;
import com.example.pethealth.dto.prescriptionDTO.PrescriptionMedicineInput;
import com.example.pethealth.enums.InvoiceStatus;
import com.example.pethealth.exception.BadRequestException;
import com.example.pethealth.model.*;
import com.example.pethealth.repositories.InvoiceRepository;
import com.example.pethealth.repositories.MedicalRepository;
import com.example.pethealth.repositories.PrescriptionMedicineRepository;
import com.example.pethealth.repositories.PrescriptionRepository;
import com.example.pethealth.service.parent.IPresciptionService;
import com.example.pethealth.service.profile.ProfileService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrescriptionService implements IPresciptionService {
    private final PrescriptionRepository prescriptionRepository;
    private final MedicalRepository medicalRepository;
    private final PrescriptionMedicineService prescriptionMedicineService;
    private final ProfileService profileService;
    private final PrescriptionMedicineRepository prescriptionMedicineRepository;

    private final InvoiceRepository invoiceRepository;
    public PrescriptionService(PrescriptionRepository prescriptionRepository, MedicalRepository medicalRepository, PrescriptionMedicineService prescriptionMedicineService, ProfileService profileService, PrescriptionMedicineRepository prescriptionMedicineRepository, InvoiceRepository invoiceRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.medicalRepository = medicalRepository;
        this.prescriptionMedicineService = prescriptionMedicineService;
        this.profileService = profileService;
        this.prescriptionMedicineRepository = prescriptionMedicineRepository;
        this.invoiceRepository = invoiceRepository;
    }


    @Override
    public BaseDTO createPrestion(PrescriptionInput prescriptionInput, long medicalReportId) {
        MedicalReport medicalReport = medicalRepository.findById(medicalReportId).orElseThrow(
                ()-> new BadRequestException("dont find medicalReport with id = " + medicalReportId)
        );
        if(medicalReport.getPrescription() == null){
            User doctor = profileService.getLoggedInUser();
            Prescription prescription = Prescription.builder()
                    .namePet(prescriptionInput.getNamePet())
                    .genderPet(prescriptionInput.getGenderPet())
                    .oldPet((long) prescriptionInput.getOldPet())
                    .owenPet(prescriptionInput.getNamePetOwen())
                    .address(prescriptionInput.getAddress())
                    .reasonForExamination(prescriptionInput.getSymptom())
                    .note(prescriptionInput.getNote())
                    .diagnosed(prescriptionInput.getDiagnosed())
                    .doctor(doctor)
                    .medicalReport(medicalReport)
                    .totalPrice(prescriptionInput.getTotalMedicine())
                    .build();
            prescriptionRepository.save(prescription);
            for(PrescriptionMedicineInput items : prescriptionInput.getMedicineInputList()){
                prescriptionMedicineService.createPrescriptionMedicine(items,prescription.getId());
            }
            return BaseDTO.builder()
                    .message("success")
                    .result(true)
                    .build();
        }
        return BaseDTO.builder()
                .message("Phiếu khám đã có hóa đơn")
                .result(false)
                .build();
    }

    @Override
    public BaseDTO updatePrescion(long id, PrescriptionInput prescriptionInput) {
        return null;
    }

    @Override
    public BaseDTO getAllPrescription() {
        return null;
    }

    @Override
    public PrescriptionDetailOutput getPrescriptionDetailWithMedicalReport(long medicalReportId) {
        MedicalReport medicalReport = medicalRepository.findById(medicalReportId).orElseThrow(
                ()-> new BadRequestException("dont find medicalReport with id = " + medicalReportId)
        );
        if(medicalReport.getPrescription() != null){
            Prescription prescription = medicalReport.getPrescription();
            List<PrescriptionMedicine> results = prescriptionMedicineRepository.findByPrescriptionId(prescription.getId());
            List<MedicineOutPut> medicineOutPutList = results.stream().map(
                    items-> MedicineOutPut.builder()
                            .id(items.getId())
                            .name(items.getMedicine().getName())
                            .countMedicine(items.getQuantity())
                            .instructionsForUse(items.getTutorialMedicine())
                            .build()
            ).collect(Collectors.toList());
            if(invoiceRepository.findByPrescriptionId(prescription.getId()).isPresent()){
                Invoice invoice = invoiceRepository.findByPrescriptionId(prescription.getId())
                        .orElseThrow(()-> new BadRequestException("dont find by id with  " + prescription.getId()));
                boolean checkIn = true;
                if(invoice.getStatus().equals(InvoiceStatus.PAID)){
                    checkIn = false;
                }
                return PrescriptionDetailOutput.builder()
                        .result(true)
                        .message("success")
                        .prescription(prescription)
                        .checkInvoice(checkIn)
                        .medicineOutPutList(medicineOutPutList)
                        .build();
            }else{
                return PrescriptionDetailOutput.builder()
                        .result(true)
                        .message("success")
                        .prescription(prescription)
                        .checkInvoice(true)
                        .medicineOutPutList(medicineOutPutList)
                        .build();
            }
        }
        if(invoiceRepository.findByMedicalReportId(medicalReportId).isPresent()){
            Invoice invoice = invoiceRepository.findByMedicalReportId(medicalReportId).orElseThrow(
                    ()-> new BadRequestException("dont find by id with " + medicalReportId)
            );
            if(invoice.getStatus().equals(InvoiceStatus.PAID)){
                return PrescriptionDetailOutput.builder()
                        .result(true)
                        .message("khong theo tao hoac sua vi phieu da duoc thanh toan")
                        .checkInvoice(false)
                        .build();
            }
        }
        return PrescriptionDetailOutput.builder()
                .result(false)
                .message("")
                .checkInvoice(true)
                .build();
    }
}
