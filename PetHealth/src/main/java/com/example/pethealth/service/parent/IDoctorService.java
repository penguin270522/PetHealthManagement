package com.example.pethealth.service.parent;

import com.example.pethealth.dto.AppointmentDTO;
import com.example.pethealth.dto.PageDTO;
import com.example.pethealth.dto.SimpleResponese;
import com.example.pethealth.enums.AppointmentStatus;
import com.example.pethealth.model.Appointment;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface IDoctorService {

    PageDTO getAppoimentAll(Map<String, String> params) throws BadRequestException;
    Appointment findById(long id);
    AppointmentDTO repairAppointment(long id, Appointment appointment);

    AppointmentDTO deleteAppointment(long id);

    AppointmentDTO creatAppointment(Appointment appointment);

    AppointmentDTO searchCodeAppointment(String code);

    PageDTO searchNameUser(Map<String,String> params);

    PageDTO findByStatus(Map<String, String> params);
}
