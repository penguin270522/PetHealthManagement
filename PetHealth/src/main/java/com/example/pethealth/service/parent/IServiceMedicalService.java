package com.example.pethealth.service.parent;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.model.ServiceMedical;
import org.springframework.stereotype.Service;

@Service
public interface IServiceMedicalService {
    BaseDTO createServiceMedical(ServiceMedical serviceMedical);
    BaseDTO getAllServiceMedical();
}
