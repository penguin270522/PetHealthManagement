package com.example.pethealth.service.parent;

import com.example.pethealth.dto.AppointmentDTO;
import com.example.pethealth.model.Appointment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IDoctorService {

    List<Appointment> getAll();
    Appointment findById(long id);
    AppointmentDTO repairAppointment(long id, Appointment appointment);

    AppointmentDTO deleteAppointment(long id);

    AppointmentDTO creatAppointment(Appointment appointment);
}
