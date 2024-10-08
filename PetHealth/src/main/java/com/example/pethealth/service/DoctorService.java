package com.example.pethealth.service;

import com.example.pethealth.components.GetPageableUtil;
import com.example.pethealth.dto.AppointmentDTO;
import com.example.pethealth.dto.PageDTO;
import com.example.pethealth.dto.SimpleResponese;
import com.example.pethealth.enums.AppointmentStatus;
import com.example.pethealth.exception.AppointmentException;
import com.example.pethealth.exception.BadRequestException;
import com.example.pethealth.model.Appointment;
import com.example.pethealth.repositories.AppointmentRepository;
import com.example.pethealth.service.parent.IDoctorService;
import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class DoctorService implements IDoctorService {

    private final AppointmentRepository appointmentRepository;

    private final ModelMapper modelMapper;

    @Override
    public PageDTO getAppoimentAll(Map<String, String> params){
        try {
            Pageable pageable = GetPageableUtil.getPageable(params);
            SimpleResponese<Appointment> appointmentSimpleResponese = appointmentRepository.findByCondition(params, pageable);
            return PageDTO.builder()
                    .message("success")
                    .result(true)
                    .simpleResponese(appointmentSimpleResponese)
                    .build();
        }catch (BadRequestException e){
            return PageDTO.builder()
                    .message(e.getMessage())
                    .result(false)
                    .build();
        }
    }

    @Override
    public Appointment findById(long id) {

        return appointmentRepository.findById(id)
                .orElseThrow(()-> new AppointmentException("dont find post id = " + id));
    }

    @Override
    public AppointmentDTO repairAppointment(long id, Appointment appointment) {
        Appointment appointmentor = appointmentRepository.getReferenceById(id);
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        if(appointmentor != null){
            appointmentor.setNameUser(appointment.getNameUser());
            appointmentor.setNumberPhone(appointment.getNumberPhone());
            appointmentor.setNamePet(appointment.getNamePet());
            appointmentor.setStartDate(appointment.getStartDate());
            appointmentor.setAppointmentStatus(appointment.getAppointmentStatus());
            appointmentRepository.save(appointmentor);
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
           Appointment appointment =  appointmentRepository.getById(id);
           if(appointment != null){
               appointmentRepository.delete(appointment);
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
        appointmentRepository.save(appointment1);
        return AppointmentDTO.builder()
                .message("success").result(true)
                .build();
    }

    @Override
    public AppointmentDTO searchCodeAppointment(String code) {

        if (code == null || code.isEmpty()) {
            return AppointmentDTO.builder()
                    .message("Mã không hợp lệ")
                    .result(false)
                    .build();
        }

        List<Appointment> appointmentList = appointmentRepository.findByCode(code);

        if(!appointmentList.isEmpty()){
            return AppointmentDTO
                    .builder()
                    .message("success")
                    .result(true)
                    .appointmentList(appointmentList)
                    .build();
        }else{
            return AppointmentDTO.builder()
                    .message("Mã không hợp lệ")
                    .result(false)
                    .build();
        }
    }

    @Override
    public PageDTO searchNameUser(Map<String,String> params) {
        try{
            Pageable pageable = GetPageableUtil.getPageable(params);
            SimpleResponese<Appointment> results = appointmentRepository.findByCondition(params,pageable);
            return PageDTO.builder()
                    .message("success")
                    .result(true)
                    .simpleResponese(results)
                    .build();
        }catch (BadRequestException e){
            return PageDTO.builder()
                    .message(e.getMessage())
                    .result(false)
                    .build();
        }
    }



    @Override
    public PageDTO findByStatus(Map<String, String> params) {
        try {
            Pageable pageable = GetPageableUtil.getPageable(params);
            SimpleResponese<Appointment> resuls = appointmentRepository.findByCondition(params, pageable);
            return PageDTO.builder()
                    .message("success")
                    .result(true)
                    .simpleResponese(resuls)
                    .build();
        }catch (BadRequestException e){
            return PageDTO.builder()
                    .message(e.getMessage())
                    .result(false)
                    .build();
        }

    }


}
