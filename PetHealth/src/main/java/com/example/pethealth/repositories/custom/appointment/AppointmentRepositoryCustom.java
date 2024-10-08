package com.example.pethealth.repositories.custom.appointment;

import com.example.pethealth.dto.SimpleResponese;
import com.example.pethealth.model.Appointment;
import org.springframework.data.domain.Pageable;


import java.util.Map;

public interface AppointmentRepositoryCustom {
     SimpleResponese<Appointment> findByCondition(Map<String,String> params, Pageable pageable);
}
