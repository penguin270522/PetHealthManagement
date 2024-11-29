package com.example.pethealth.controller.prescription;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.model.Medicine;
import com.example.pethealth.service.MedicineService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/medicine")
public class MedicineController {
    private final MedicineService medicineService;

    public MedicineController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @PostMapping("/createMedicine/{id}")
    public BaseDTO createMedicine(@RequestBody Medicine medicine, @PathVariable long id){
        return medicineService.createMedicine(medicine,id);
    }
    @GetMapping("/getAllMedicine")
    public BaseDTO getAllMedicine(){
        return medicineService.getAllMedicine();
    }

    @GetMapping("/typeMedicine/{id}")
    public BaseDTO getAllMedicineWithTypeMedicine(@PathVariable long id){
        return medicineService.getAllMedicineWithTypeMedicine(id);
    }
}
