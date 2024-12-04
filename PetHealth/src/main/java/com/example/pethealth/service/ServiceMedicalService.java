package com.example.pethealth.service;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.model.ServiceMedical;
import com.example.pethealth.repositories.ServiceMedicalRepository;
import com.example.pethealth.service.parent.IServiceMedicalService;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceMedicalService implements IServiceMedicalService {
    private final ServiceMedicalRepository serviceMedicalRepository;

    public ServiceMedicalService(ServiceMedicalRepository serviceMedicalRepository) {
        this.serviceMedicalRepository = serviceMedicalRepository;
    }

    @Override
    public BaseDTO createServiceMedical(ServiceMedical serviceMedical) {
        serviceMedicalRepository.save(serviceMedical);
        return BaseDTO.builder()
                .result(true).message("success")
                .build();
    }
    @Override
    public BaseDTO getAllServiceMedical() {
        List<ServiceMedical> medicalList = serviceMedicalRepository.findAll();
        return BaseDTO.builder()
                .result(true)
                .message("success")
                .results(medicalList)
                .build();
    }
}
