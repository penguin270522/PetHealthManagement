package com.example.pethealth.service.parent;

import com.example.pethealth.dto.appointmentDTO.DoctorRepair;
import com.example.pethealth.dto.outputDTO.AppointmentDTO;
import com.example.pethealth.dto.outputDTO.AppointmentResponse;
import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.dto.outputDTO.PageDTO;
import com.example.pethealth.dto.output.ListAppointToDay;
import com.example.pethealth.model.Appointment;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
public interface IDoctorService {

    ListAppointToDay getAppointmentWithDateNow(Map<String,String> params, LocalDate dateNow);

    PageDTO getAppoimentAll(Map<String, String> params) throws BadRequestException;
    Appointment findById(long id);
    AppointmentDTO repairAppointment(long id, DoctorRepair appointment);

    AppointmentDTO deleteAppointment(long id);

    AppointmentDTO creatAppointment(AppointmentResponse appointment);
    AppointmentDTO createAppointmentWithAccount(AppointmentResponse appointment);

    AppointmentDTO searchCodeAppointment(String code);

    PageDTO searchNameUser(Map<String,String> params);

    PageDTO findByStatus(Map<String, String> params);

    BaseDTO getAppointmentDetails(Long id);

    PageDTO getAppointmentActiveDaily(Map<String , String> params);

}
