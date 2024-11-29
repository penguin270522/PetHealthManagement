package com.example.pethealth.controller.prescription;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.model.TypeMedicine;
import com.example.pethealth.service.TypeMedicineService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/typeMedicine")
public class TypeMedicineController {
    private final TypeMedicineService typeMedicineService;
    public TypeMedicineController(TypeMedicineService typeMedicineService) {
        this.typeMedicineService = typeMedicineService;
    }
    @PostMapping("/createTypeMedicine")
    public BaseDTO createTypeMedicine(@RequestBody TypeMedicine typeMedicine){
        return typeMedicineService.createTypeMedicine(typeMedicine);
    }
    @GetMapping("/getAllTypeMedicine")
    public BaseDTO getAllTypeMedicine(){
        return typeMedicineService.getAllMedicine();
    }
    @DeleteMapping("/deleteTypeMedicine/{id}")
    public BaseDTO deteleTypeMedicine(@PathVariable long id){
        return typeMedicineService.deleteTypeMedicine(id);
    }
}
