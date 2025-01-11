package com.example.pethealth.service;

import com.example.pethealth.components.GetPageableUtil;
import com.example.pethealth.components.MapperDateUtil;
import com.example.pethealth.dto.appointmentDTO.AppointmentOutputDoctor;
import com.example.pethealth.dto.appointmentDTO.DoctorAppointment;
import com.example.pethealth.dto.appointmentDTO.DoctorRepair;
import com.example.pethealth.dto.outputDTO.*;
import com.example.pethealth.dto.appointmentDTO.AppointmentDoctorOutput;
import com.example.pethealth.dto.output.ListAppointToDay;
import com.example.pethealth.enums.AppointmentStatus;
import com.example.pethealth.exception.AppointmentException;
import com.example.pethealth.exception.BadRequestException;
import com.example.pethealth.model.*;
import com.example.pethealth.repositories.AppointmentRepository;
import com.example.pethealth.repositories.PetRepository;
import com.example.pethealth.repositories.ServiceMedicalRepository;
import com.example.pethealth.repositories.WorkDayRepository;
import com.example.pethealth.service.auth.UserService;
import com.example.pethealth.service.parent.IDoctorService;
import com.example.pethealth.service.profile.ProfileService;
import lombok.AllArgsConstructor;


import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;

import java.time.LocalTime;
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
    private final WorkDayRepository workDayRepository;


    @Override
    public ListAppointToDay getAppointmentWithDateNow(Map<String, String> params, LocalDate dateNow) {
        Pageable pageable = GetPageableUtil.getPageable(params);
        SimpleResponese<Appointment> appointmentSimpleResponese = appointmentRepository.findByCondition(params,pageable);
        List<AppointmentDoctorOutput> results = new ArrayList<>();
        for(Appointment items: appointmentSimpleResponese.results){
            if(MapperDateUtil.checkDate(dateNow,items.getStartDate()) && items.getAppointmentStatus() == AppointmentStatus.ACTIVE){
                AppointmentDoctorOutput appointmentDoctorOutput = new AppointmentDoctorOutput();
                appointmentDoctorOutput.setNamePet(items.getNamePet());
                appointmentDoctorOutput.setId(items.getId());
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
                DoctorAppointment doctorAppointment = new DoctorAppointment();
                if(items.getDoctorInCharge() != null){
                    doctorAppointment = DoctorAppointment.builder()
                            .fullNameDoctor(items.getDoctorInCharge().getFullName())
                            .id(items.getDoctorInCharge().getId())
                            .build();
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
                        .doctorAppointment(doctorAppointment)
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
    public AppointmentDTO repairAppointment(long id, DoctorRepair appointmentDTO) {
        try{
            Appointment appointment = appointmentRepository.findById(id).orElseThrow(
                    ()-> new BadRequestException("dont find by id = " + id)
            );
            boolean isWorksame = true;
            User doctor = profileService.getLoggedInUser();
            List<Appointment> appointmentList = appointmentRepository.getAppointmentByStartDate(appointment.getStartDate().toLocalDate(), doctor);
            for(Appointment items: appointmentList){
                LocalTime checkTime = appointment.getStartDate().toLocalTime();
                if (!checkTime.isBefore(items.getStartDate().toLocalTime()) && !checkTime.isAfter(items.getStartDate().toLocalTime().plusHours(1))) {
                    isWorksame = false;
                    break;
                }
            }
            if(isWorksame){
                appointment.setAppointmentStatus(appointmentDTO.getAppointmentStatus());
                appointment.setReplayAppointment(appointmentDTO.getReplayAppointment());
                if(appointmentDTO.getDoctorId() != null && appointmentDTO.getDoctorId() > 0){
                    User doctorChanger = userService.findByIdUser(appointmentDTO.getDoctorId());
                    appointment.setDoctorInCharge(doctorChanger);
                }
                appointmentRepository.save(appointment);
                return AppointmentDTO.builder()
                        .result(true)
                        .message("Thay Đổi phiếu khám thành công")
                        .build();
            }
            return AppointmentDTO.builder()
                    .result(false)
                    .message("Đã có lịch hẹn vào giờ này!")
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
                () -> new BadRequestException("Không tìm thấy dịch vụ với ID = " + appointment.getServiceId())
        );
        List<WorkDay> workDayList = workDayRepository.findAll();
        boolean isWithinWorkTime = false;
        for (WorkDay items : workDayList) {
            LocalTime checkTime = appointment.getDateTime().toLocalTime();
            if (!checkTime.isBefore(items.getStartTime()) && !checkTime.isAfter(items.getEndTime())) {
                isWithinWorkTime = true;
                break;
            }
        }
        if (!isWithinWorkTime) {
            return AppointmentDTO.builder()
                    .message("Thời gian không nằm trong khoảng làm việc.")
                    .result(false)
                    .build();
        }
        boolean isWorksame = true;
        List<Appointment> appointmentList = appointmentRepository.getAppointmentByStartDate(appointment.getDateTime().toLocalDate(), user);
        for(Appointment items: appointmentList){
            LocalTime checkTime = appointment.getDateTime().toLocalTime();
            if (!checkTime.isBefore(items.getStartDate().toLocalTime()) && !checkTime.isAfter(items.getStartDate().toLocalTime().plusHours(1))) {
                isWorksame = false;
                break;
            }
        }
        if(isWorksame){
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
                    .message("Thành công.")
                    .result(true)
                    .build();
        }
        return AppointmentDTO.builder()
                .message("Đã trùng lịch.")
                .result(false)
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

        List<WorkDay> workDayList = workDayRepository.findAll();
        boolean isWithinWorkTime = false;
        for (WorkDay items : workDayList) {
            LocalTime checkTime = appointment.getDateTime().toLocalTime();
            if (!checkTime.isBefore(items.getStartTime()) && !checkTime.isAfter(items.getEndTime())) {
                isWithinWorkTime = true;
                break;
            }
        }
        if (!isWithinWorkTime) {
            return AppointmentDTO.builder()
                    .message("Thời gian không nằm trong khoảng làm việc.")
                    .result(false)
                    .build();
        }
        boolean isWorksame = true;
        List<Appointment> appointmentList = appointmentRepository.getAppointmentByStartDate(appointment.getDateTime().toLocalDate(), doctor);
        for(Appointment items: appointmentList){
            LocalTime checkTime = appointment.getDateTime().toLocalTime();
            if (!checkTime.isBefore(items.getStartDate().toLocalTime()) && !checkTime.isAfter(items.getStartDate().toLocalTime().plusHours(1))) {
                isWorksame = false;
                break;
            }
        }
        if(isWorksame){
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
                    .message("Tạo phiếu khám thành công").result(true)
                    .build();
        }
        return AppointmentDTO.builder()
                .message("Đã trùng lịch.")
                .result(false)
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
            List<AppointmentOutputDoctor> appointmentDoctorOutputs = new ArrayList<>();
            for(Appointment items : results.results){
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
                    .page(results.page)
                    .totalPage(results.totalPage)
                    .totalItem(results.totalItem)
                    .limit(results.limit)
                    .totalActive(results.totalActive)
                    .totalDisable(results.totalDisable)
                    .totalPending(results.totalPending)
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
                        .id(items.getId())
                        .codeAppointment(items.getCode())
                        .fullNameUser(items.getNameUser())
                        .namePet(items.getNamePet())
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

    @Override
    public BaseDTO getAppointmentDetails(Long id) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(
                ()-> new BadRequestException("dont find by appointment with id = " + id )
        );
        String status = "";
        if(appointment.getAppointmentStatus() == AppointmentStatus.ACTIVE){
            status = "được duyệt";
        }
        if(appointment.getAppointmentStatus() == AppointmentStatus.DISABLE){
            status = "đã hủy";
        }
        if(appointment.getAppointmentStatus() == AppointmentStatus.PENDING){
            status = "đang chờ";
        }
        String urlImagePet = "http://localhost:8080/uploads/222bf6eb-aa92-4d59-b687-55d760597b5a_Screenshot%202024-11-13%20021405.png";
        String urlImageUser = "http://localhost:8080/uploads/cfef7801-ffdf-438f-b296-d697d983dd6d_Screenshot%202024-04-19%20233256.png";
        if(appointment.getPet() != null){
            urlImagePet = "http://localhost:8080/uploads/" + appointment.getPet().getImagePet().get(0).getUrl();
            urlImageUser = "http://localhost:8080/uploads/" + appointment.getPet().getUser().getImage().getUrl();
        }
        String serviceMedical = "";
        if(appointment.getServiceMedical() != null){
            serviceMedical = appointment.getServiceMedical().getName();
        }
        AppointmentOutputDoctor appointmentOutputDoctor = AppointmentOutputDoctor.builder()
                .id(appointment.getId())
                .codeAppointment(appointment.getCode())
                .fullNameUser(appointment.getNameUser())
                .namePet(appointment.getNamePet())
                .numberPhone(appointment.getNumberPhone())
                .startDate(MapperDateUtil.formatLocalDateTime(appointment.getStartDate()))
                .statusAppointment(status)
                .serviceMedical(serviceMedical)
                .urlPet(urlImagePet)
                .urlUser(urlImageUser)
                .message(appointment.getMessage())
                .build();
        return BaseDTO.builder()
                .result(true).message("success").object(appointmentOutputDoctor)
                .build();
    }

    @Override
    public PageDTO getAppointmentActiveDaily(Map<String, String> params) {
        Pageable pageable  = GetPageableUtil.getPageable(params);
        SimpleResponese<Appointment> responese = appointmentRepository.findByCondition(params, pageable);
        List<AppointmentDoctorOutput> results = new ArrayList<>();
        for(Appointment items: responese.results){
                AppointmentDoctorOutput appointmentDoctorOutput = new AppointmentDoctorOutput();
                appointmentDoctorOutput.setId(items.getId());
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
                if(items.getPet() != null){
                    if(items.getPet().getImagePet().isEmpty()){
                        appointmentDoctorOutput.setUrl("http://localhost:8080/uploads/222bf6eb-aa92-4d59-b687-55d760597b5a_Screenshot%202024-11-13%20021405.png");
                    }else{
                        for(Image image : items.getPet().getImagePet()){
                            appointmentDoctorOutput.setUrl("http://localhost:8080/uploads/" + image.getUrl());
                            break;
                        }
                    }

                }else{
                    appointmentDoctorOutput.setUrl("http://localhost:8080/uploads/222bf6eb-aa92-4d59-b687-55d760597b5a_Screenshot%202024-11-13%20021405.png");
                }
                if(items.getAppointmentStatus() == AppointmentStatus.ACTIVE){
                    appointmentDoctorOutput.setStatus("Đã duyệt");
                }
                appointmentDoctorOutput.setMessage(responese.message);
                results.add(appointmentDoctorOutput);
        }
        SimpleResponese<AppointmentDoctorOutput> simpleResponese = SimpleResponese.<AppointmentDoctorOutput>builder()
                .results(results)
                .page(responese.getPage())
                .message(responese.getMessage())
                .limit(responese.getLimit())
                .totalItem(responese.getTotalItem())
                .build();

        return PageDTO.builder()
                .message("success").result(true).simpleResponese(simpleResponese)
                .build();
    }
}
