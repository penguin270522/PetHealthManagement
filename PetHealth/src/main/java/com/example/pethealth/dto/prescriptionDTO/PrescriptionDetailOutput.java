package com.example.pethealth.dto.prescriptionDTO;

import com.example.pethealth.model.Medicine;
import com.example.pethealth.model.Prescription;
import com.example.pethealth.model.PrescriptionMedicine;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrescriptionDetailOutput {
    private String message;
    private boolean result;
    private Prescription prescription;
    private List<MedicineOutPut> medicineOutPutList;
    private boolean checkInvoice;
}
