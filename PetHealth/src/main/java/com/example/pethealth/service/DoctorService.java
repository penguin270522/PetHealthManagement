package com.example.pethealth.service;

import com.example.pethealth.components.GetPageableUtil;
import com.example.pethealth.components.MapperDateUtil;
import com.example.pethealth.dto.output.AppointmentOutputDoctor;
import com.example.pethealth.dto.outputDTO.AppointmentDTO;
import com.example.pethealth.dto.outputDTO.AppointmentResponse;
import com.example.pethealth.dto.outputDTO.PageDTO;
import com.example.pethealth.dto.outputDTO.SimpleResponese;
import com.example.pethealth.dto.output.AppointmentDoctorOutput;
import com.example.pethealth.dto.output.ListAppointToDay;
import com.example.pethealth.enums.AppointmentStatus;
import com.example.pethealth.exception.AppointmentException;
import com.example.pethealth.exception.BadRequestException;
import com.example.pethealth.model.*;
import com.example.pethealth.repositories.AppointmentRepository;
import com.example.pethealth.repositories.PetRepository;
import com.example.pethealth.repositories.ServiceMedicalRepository;
import com.example.pethealth.service.auth.UserService;
import com.example.pethealth.service.parent.IDoctorService;
import com.example.pethealth.service.profile.ProfileService;
import lombok.AllArgsConstructor;


import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class DoctorService implements IDoctorService {

    private final AppointmentRepository appointmentRepository;
    private final UserService userService;
    private final PetRepository petRepository;
    private final ProfileService profileService;
    private final ServiceMedicalRepository serviceMedicalRepository;

    @Override
    public ListAppointToDay getAppointmentWithDateNow(Map<String, String> params, LocalDate dateNow) {
        Pageable pageable = GetPageableUtil.getPageable(params);
        SimpleResponese<Appointment> appointmentSimpleResponese = appointmentRepository.findByCondition(params,pageable);
        List<AppointmentDoctorOutput> results = new ArrayList<>();
        for(Appointment items: appointmentSimpleResponese.results){
            if(MapperDateUtil.checkDate(dateNow,items.getStartDate()) && items.getAppointmentStatus() == AppointmentStatus.ACTIVE){
                AppointmentDoctorOutput appointmentDoctorOutput = new AppointmentDoctorOutput();
                appointmentDoctorOutput.setNamePet(items.getNamePet());
                appointmentDoctorOutput.setDateAppointment(MapperDateUtil.formatLocalDateTime(items.getStartDate()));
                appointmentDoctorOutput.setDate(MapperDateUtil.getDate(items.getStartDate()));
                appointmentDoctorOutput.setTime(MapperDateUtil.getTime(items.getStartDate()));
                appointmentDoctorOutput.setFullNameUser(items.getNameUser());
                String service = "Không có dịch vụ!";
                if(items.getServiceMedical() != null){
                    appointmentDoctorOutput.setServiceMedical(items.getServiceMedical().getName());
                }
                appointmentDoctorOutput.setServiceMedical(service);
                for(Image image : items.getPet().getImagePet()){
                    appointmentDoctorOutput.setUrl("http://localhost:8080/uploads/" + image.getUrl());
                    break;
                }
                if(items.getAppointmentStatus() == AppointmentStatus.ACTIVE){
                    appointmentDoctorOutput.setStatus("Đã duyệt");
                }
                appointmentDoctorOutput.setMessage(appointmentSimpleResponese.message);
                results.add(appointmentDoctorOutput);
            }
        }
        return ListAppointToDay.builder()
                .results(true)
                .message("success")
                .appointmentDoctorOutputs(results)
                .totalAppointToday(results.size())
                .build();
    }

    @Override
    public PageDTO getAppoimentAll(Map<String, String> params){
        try {
            Pageable pageable = GetPageableUtil.getPageable(params);
            SimpleResponese<Appointment> appointmentSimpleResponese = appointmentRepository.findByCondition(params, pageable);
            List<AppointmentOutputDoctor> appointmentDoctorOutputs = new ArrayList<>();
            for(Appointment items : appointmentSimpleResponese.results){
                String status = "";
                if(items.getAppointmentStatus() == AppointmentStatus.ACTIVE){
                    status = "được duyệt";
                }
                if(items.getAppointmentStatus() == AppointmentStatus.DISABLE){
                    status = "đã hủy";
                }
                if(items.getAppointmentStatus() == AppointmentStatus.PENDING){
                    status = "đang chờ";
                }
                if(items.getAppointmentStatus() == AppointmentStatus.COMPLETE){
                    status = "đã hoàn thành";
                }
                String urlImagePet = "http://localhost:8080/uploads/222bf6eb-aa92-4d59-b687-55d760597b5a_Screenshot%202024-11-13%20021405.png";
                String urlImageUser = "http://localhost:8080/uploads/cfef7801-ffdf-438f-b296-d697d983dd6d_Screenshot%202024-04-19%20233256.png";
                if(items.getPet() != null){
                    urlImagePet = "http://localhost:8080/uploads/" + items.getPet().getImagePet().get(0).getUrl();
                    urlImageUser = "http://localhost:8080/uploads/" + items.getPet().getUser().getImage().getUrl();
                }
                String typeClients;
                if(items.getPet() == null){
                    typeClients = "Người dùng khách";
                }else{
                    typeClients = "Thành viên";
                }
                String serviceMedical;
                if(items.getServiceMedical() == null){
                    serviceMedical = "không có dịch vụ";
                }else{
                    serviceMedical = items.getServiceMedical().getName();
                }
                AppointmentOutputDoctor appointmentOutputDoctor = AppointmentOutputDoctor.builder()
                        .id(items.getId())
                        .fullNameUser(items.getNameUser())
                        .namePet(items.getNamePet())
                        .message(items.getMessage())
                        .codeAppointment(items.getCode())
                        .numberPhone(items.getNumberPhone())
                        .startDate(MapperDateUtil.formatLocalDateTime(items.getStartDate()))
                        .statusAppointment(status)
                        .urlPet(urlImagePet)
                        .urlUser(urlImageUser)
                        .typeClient(typeClients)
                        .serviceMedical(serviceMedical)
                        .build();
                appointmentDoctorOutputs.add(appointmentOutputDoctor);
            }
            SimpleResponese<AppointmentOutputDoctor> responese = SimpleResponese.<AppointmentOutputDoctor>builder()
                    .results(appointmentDoctorOutputs)
                    .message("sucess")
                    .page(appointmentSimpleResponese.page)
                    .totalPage(appointmentSimpleResponese.totalPage)
                    .totalItem(appointmentSimpleResponese.totalItem)
                    .limit(appointmentSimpleResponese.limit)
                    .totalActive(appointmentSimpleResponese.totalActive)
                    .totalDisable(appointmentSimpleResponese.totalDisable)
                    .totalPending(appointmentSimpleResponese.totalPending)
                    .build();
            return PageDTO.builder()
                    .message("success")
                    .result(true)
                    .simpleResponese(responese)
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
    public AppointmentDTO repairAppointment(long id, Appointment appointmentDTO) {
        try{
            Appointment appointment = appointmentRepository.findById(id).orElseThrow(
                    ()-> new BadRequestException("dont find by id = " + id)
            );
            appointment.setAppointmentStatus(appointmentDTO.getAppointmentStatus());
            appointment.setReplayAppointment(appointmentDTO.getReplayAppointment());
            appointmentRepository.save(appointment);
            return AppointmentDTO.builder()
                    .result(true)
                    .message("success")
                    .build();
        }catch (BadRequestException e){
            return AppointmentDTO.builder()
                    .result(false)
                    .message(e.getMessage())
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
    public AppointmentDTO creatAppointment(AppointmentResponse appointment) {
        User user = userService.findByIdUser(appointment.getDoctorId());
        ServiceMedical serviceMedical = serviceMedicalRepository.findById(appointment.getServiceId()).orElseThrow(
                ()-> new BadRequestException("dont find serviceMedical with id = " + appointment.getServiceId())
        );
        Appointment appointment1 = new Appointment();
        appointment1.setAppointmentStatus(AppointmentStatus.PENDING);
        appointment1.setServiceMedical(serviceMedical);
        appointment1.setNameUser(appointment.getUsername());
        appointment1.setNamePet(appointment.getNamePet());
        appointment1.setNumberPhone(appointment.getNumberPhone());
        appointment1.setStartDate(appointment.getDateTime());
        appointment1.setCreatedDate(LocalDateTime.now());
        appointment1.setDoctorInCharge(user);
        appointment1.setMessage(appointment.getMessage());
        appointmentRepository.save(appointment1);
        return AppointmentDTO.builder()
                .message("success").result(true)
                .build();
    }

    @Override
    public AppointmentDTO createAppointmentWithAccount(AppointmentResponse appointment) {
        User doctor = userService.findByIdUser(appointment.getDoctorId());
        User user = profileService.getLoggedInUser();
        Pet pet = petRepository.findById(appointment.getPetId()).orElseThrow(
                ()-> new BadRequestException("dont find by pet with id" + appointment.getPetId())
        );
        ServiceMedical serviceMedical = serviceMedicalRepository.findById(appointment.getServiceId()).orElseThrow(
                ()-> new BadRequestException("dont find by Service with id " + appointment.getServiceId())
        );
        Appointment appointment1 = new Appointment();
        appointment1.setAppointmentStatus(AppointmentStatus.PENDING);
        appointment1.setNameUser(user.getFullName());
        appointment1.setNamePet(appointment.getNamePet());
        appointment1.setNumberPhone(user.getPhoneNumber());
        appointment1.setStartDate(appointment.getDateTime());
        appointment1.setCreatedDate(LocalDateTime.now());
        appointment1.setDoctorInCharge(doctor);
        appointment1.setPet(pet);
        appointment1.setServiceMedical(serviceMedical);
        appointment1.setMessage(appointment.getMessage());
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
            List<AppointmentOutputDoctor> appointmentDoctorOutputs = new ArrayList<>();
            for(Appointment items : resuls.results){
                String status = "";
                if(items.getAppointmentStatus() == AppointmentStatus.ACTIVE){
                    status = "được duyệt";
                }
                if(items.getAppointmentStatus() == AppointmentStatus.DISABLE){
                    status = "đã hủy";
                }
                if(items.getAppointmentStatus() == AppointmentStatus.PENDING){
                    status = "đang chờ";
                }
                String urlImagePet = "http://localhost:8080/uploads/222bf6eb-aa92-4d59-b687-55d760597b5a_Screenshot%202024-11-13%20021405.png";
                String urlImageUser = "http://localhost:8080/uploads/cfef7801-ffdf-438f-b296-d697d983dd6d_Screenshot%202024-04-19%20233256.png";
                if(items.getPet() != null){
                    urlImagePet = "http://localhost:8080/uploads/" + items.getPet().getImagePet().get(0).getUrl();
                    urlImageUser = "http://localhost:8080/uploads/" + items.getPet().getUser().getImage().getUrl();
                }
                AppointmentOutputDoctor appointmentOutputDoctor = AppointmentOutputDoctor.builder()
                        .codeAppointment(items.getCode())
                        .numberPhone(items.getNumberPhone())
                        .startDate(MapperDateUtil.formatLocalDateTime(items.getStartDate()))
                        .statusAppointment(status)
                        .urlPet(urlImagePet)
                        .urlUser(urlImageUser)
                        .build();
                appointmentDoctorOutputs.add(appointmentOutputDoctor);
            }
            SimpleResponese<AppointmentOutputDoctor> responese = SimpleResponese.<AppointmentOutputDoctor>builder()
                    .results(appointmentDoctorOutputs)
                    .message("sucess")
                    .page(resuls.page)
                    .totalPage(resuls.totalPage)
                    .totalItem(resuls.totalItem)
                    .limit(resuls.limit)
                    .totalActive(resuls.totalActive)
                    .totalDisable(resuls.totalDisable)
                    .totalPending(resuls.totalPending)
                    .build();
            return PageDTO.builder()
                    .message("success")
                    .result(true)
                    .simpleResponese(responese)
                    .build();
        }catch (BadRequestException e){
            return PageDTO.builder()
                    .message(e.getMessage())
                    .result(false)
                    .build();
        }
    }
}
