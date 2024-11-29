package com.example.pethealth.controller;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.model.MedicalReport;
import com.example.pethealth.model.ServiceMedical;
import com.example.pethealth.service.ServiceMedicalService;
import com.example.pethealth.service.parent.IServiceMedicalService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/serviceMedical")
public class ServiceMedicalController {

    private final IServiceMedicalService iServiceMedicalService;

    public ServiceMedicalController(ServiceMedicalService iServiceMedicalService) {
        this.iServiceMedicalService = iServiceMedicalService;
    }


    @PostMapping("/createService")
    public BaseDTO createService(@RequestBody ServiceMedical serviceMedical){
        return iServiceMedicalService.createServiceMedical(serviceMedical);
    }

    @GetMapping
    public BaseDTO getAllServiceMedical(){
        return iServiceMedicalService.getAllServiceMedical();
    }
}
