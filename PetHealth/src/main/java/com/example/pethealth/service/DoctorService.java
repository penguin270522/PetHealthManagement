package com.example.pethealth.service;

import com.example.pethealth.dto.AppointmentDTO;
import com.example.pethealth.enums.AppointmentStatus;
import com.example.pethealth.exception.AppointmentException;
import com.example.pethealth.model.Appointment;
import com.example.pethealth.repositories.DoctorRepository;
import com.example.pethealth.service.parent.IDoctorService;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class DoctorService implements IDoctorService {

    private final DoctorRepository doctorRepository;

    @Override
    public List<Appointment> getAll() {
        return doctorRepository.findAll();
    }

    @Override
    public Appointment findById(long id) {

        return doctorRepository.findById(id)
                .orElseThrow(()-> new AppointmentException("dont find post id = " + id));
    }

    @Override
    public AppointmentDTO repairAppointment(long id, Appointment appointment) {
        Appointment appointmentor = doctorRepository.getReferenceById(id);
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        if(appointmentor != null){
            appointmentor.setNameUser(appointment.getNameUser());
            appointmentor.setNumberPhone(appointment.getNumberPhone());
            appointmentor.setNamePet(appointment.getNamePet());
            appointmentor.setStartDate(appointment.getStartDate());
            appointmentor.setAppointmentStatus(appointment.getAppointmentStatus());
            doctorRepository.save(appointmentor);
            return appointmentDTO.builder()
                    .message("success")
                    .result(true)
                    .build();
        }else{
            return appointmentDTO.builder()
                    .message("khong tim thay id" + id)
                    .result(false)
                    .build();
        }
    }

    @Override
    public AppointmentDTO deleteAppointment(long id) {
        try{
           Appointment appointment =  doctorRepository.getById(id);
           if(appointment != null){
               doctorRepository.delete(appointment);
           }
           return new AppointmentDTO().builder()
                   .message("success")
                   .result(true)
                   .build();
        }catch (Exception e){
            return new AppointmentDTO()
                    .builder()
                    .message("fails")
                    .result(false)
                    .build();
        }
    }

    @Override
    public AppointmentDTO creatAppointment(Appointment appointment) {
        Appointment appointment1 = new Appointment();
        appointment1.setAppointmentStatus(AppointmentStatus.PENDING);
        appointment1.setNameUser(appointment.getNameUser());
        appointment1.setNamePet(appointment.getNamePet());
        appointment1.setNumberPhone(appointment.getNumberPhone());
        appointment1.setStartDate(appointment.getStartDate());
        appointment1.setCreatedDate(new Date());
        return AppointmentDTO.builder()
                .message("success").result(true)
                .build();
    }
}
