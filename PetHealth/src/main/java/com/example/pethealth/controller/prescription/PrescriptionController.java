package com.example.pethealth.controller.prescription;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.dto.prescriptionDTO.PrescriptionDetailOutput;
import com.example.pethealth.dto.prescriptionDTO.PrescriptionInput;
import com.example.pethealth.service.PrescriptionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prescription")
public class PrescriptionController {
    private final PrescriptionService prescriptionService;

    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @PostMapping("/createPrescription/{id}")
    public BaseDTO createPrescription(@PathVariable long id,@RequestBody PrescriptionInput prescriptionInput){
        return prescriptionService.createPrestion(prescriptionInput, id);
    }

    @GetMapping("/prescriptionDetails/{id}")
    public PrescriptionDetailOutput getPrescriptionDetails(@PathVariable long id){
        return prescriptionService.getPrescriptionDetailWithMedicalReport(id);
    }
}
